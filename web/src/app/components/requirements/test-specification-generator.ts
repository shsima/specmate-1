import { CEGEffectNode } from '../../model/CEGEffectNode';
import { CEGCauseNode } from '../../model/CEGCauseNode';
import { CEGNode } from '../../model/CEGNode';
import { Type } from '../../util/Type';
import { Sort } from '../../util/Sort';
import { IContainer } from '../../model/IContainer';
import { EditorCommonControlService } from '../../services/editor-common-control.service';
import { Config } from '../../config/config';
import { Url } from '../../util/Url';
import { Id } from '../../util/Id';
import { TestSpecification } from '../../model/TestSpecification';
import { Requirement } from '../../model/Requirement';
import { IContentElement } from '../../model/IContentElement';
import { CEGModel } from '../../model/CEGModel';
import { NavigatorService } from '../../services/navigator.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationModal } from '../core/forms/confirmation-modal.service';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { SpecmateViewBase } from '../core/views/specmate-view-base';

export abstract class TestSpecificationGenerator extends SpecmateViewBase {
    
    protected requirementContents: IContentElement[];
    protected requirement: Requirement;
    protected allTestSpecifications: TestSpecification[];

    private canGenerateTestSpecMap: { [url: string]: boolean } = {};
   
    constructor(dataService: SpecmateDataService, modal: ConfirmationModal, route: ActivatedRoute, navigator: NavigatorService, editorCommonControlService: EditorCommonControlService) {
        super(dataService, navigator, route, modal, editorCommonControlService)
    }
    
    protected onElementResolved(element: IContainer): void {
        this.resolveRequirement(element).then((requirement: Requirement) => this.init(requirement));
    }

    private init(requirement: Requirement): void {
        this.requirement = requirement;
        this.dataService.readContents(this.requirement.url).then((
            contents: IContainer[]) => {
            this.requirementContents = contents;
            for (let i = 0; i < this.requirementContents.length; i++) {
                let currentElement: IContainer = this.requirementContents[i];
                if (!Type.is(currentElement, CEGModel)) {
                    continue;
                }
                this.initCanCreateTestSpec(currentElement);
            }
        });
        this.readAllTestSpecifications();
    }

    protected abstract resolveRequirement(element: IContainer): Promise<Requirement>;

    private initCanCreateTestSpec(currentElement: IContainer): void {
        this.dataService.readContents(currentElement.url).then((contents: IContainer[]) => {
            this.doCheckCanCreateTestSpec(currentElement, contents);
        });
    }

    private static isNode(element: IContainer): boolean {
        return (Type.is(element, CEGNode) || Type.is(element, CEGCauseNode) || Type.is(element, CEGEffectNode));
    }

    private static hasNodes(contents: IContainer[]): boolean {
        return contents.filter((element: IContainer) => TestSpecificationGenerator.isNode(element)).length > 0;
    }

    protected doCheckCanCreateTestSpec(currentElement: IContainer, contents: IContainer[]): void {
        let hasSingleNode: boolean = contents.some((element: IContainer) => {
            let isNode: boolean = TestSpecificationGenerator.isNode(element);
            if (!isNode) {
                return false;
            }
            let node: CEGNode = element as CEGNode;
            let hasIncomingConnections: boolean = node.incomingConnections && node.incomingConnections.length > 0;
            let hasOutgoingConnections: boolean = node.outgoingConnections && node.outgoingConnections.length > 0;
            return !hasIncomingConnections && !hasOutgoingConnections;
        });
        this.canGenerateTestSpecMap[currentElement.url] = !hasSingleNode && TestSpecificationGenerator.hasNodes(contents);
    }

    protected readAllTestSpecifications(){
        this.dataService.performQuery(this.requirement.url, 'listRecursive', { class: TestSpecification.className }).then(
            (testSpecifications: TestSpecification[]) => this.allTestSpecifications = Sort.sortArray(testSpecifications));
    }

    protected canCreateTestSpecification(ceg: CEGModel): boolean {
        return this.canGenerateTestSpecMap[ceg.url];
    }

    protected generateTestSpecification(ceg: CEGModel): void {
        if (!this.requirementContents) {
            return;
        }
        if(!this.canCreateTestSpecification(ceg)) {
            return;
        }
        let testSpec: TestSpecification = new TestSpecification();
        testSpec.id = Id.uuid;
        testSpec.url = Url.build([ceg.url, testSpec.id]);
        testSpec.name = Config.TESTSPEC_NAME;
        testSpec.description = Config.TESTSPEC_DESCRIPTION;
        this.modal.confirmSave()
            .then(() => this.dataService.createElement(testSpec, true, Id.uuid))
            .then(() => this.dataService.commit('Create'))
            .then(() => this.dataService.performOperations(testSpec.url, 'generateTests'))
            .then(() => this.navigator.navigate(testSpec))
            .catch(() => {});
    }

    protected createTestSpecification(): void {
        if (!this.requirementContents) {
            return;
        }

        let testSpec: TestSpecification = new TestSpecification();
        testSpec.id = Id.uuid;
        testSpec.url = Url.build([this.requirement.url, testSpec.id]);
        testSpec.name = Config.TESTSPEC_NAME;
        testSpec.description = Config.TESTSPEC_DESCRIPTION;
        this.dataService.createElement(testSpec, true, Id.uuid)
            .then(() => this.dataService.commit('Create'))
            .then(() => this.navigator.navigate(testSpec));
    }
}