import { ChangeDetectorRef, Component, OnDestroy } from '@angular/core';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ViewControllerService } from '../../../../views/controller/modules/view-controller/services/view-controller.service';
import { ConfirmationModal } from '../../modals/services/confirmation-modal.service';
import { LoadingModalService } from '../../modals/services/loading-model-service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
    moduleId: module.id.toString(),
    selector: 'operation-monitor',
    templateUrl: 'operation-monitor.component.html'
})
export class OperationMonitor implements OnDestroy {
    loadingModalRef: NgbModalRef;

    ngOnDestroy(): void {
        this.dataServiceSubscription.unsubscribe();
        if (this.changeDetectorRef) {
            this.changeDetectorRef.detach();
        }
    }

    public isLoading: boolean;
    private dataServiceSubscription: any;

    public get taskName(): string {
        return this.dataService.currentTaskName;
    }

    constructor(
        private dataService:
        SpecmateDataService,
        private viewController: ViewControllerService,
        private changeDetectorRef: ChangeDetectorRef,
        private loadingModal: LoadingModalService) {

        this.isLoading = this.dataService.isLoading;
        this.dataServiceSubscription = 
            this.dataService.stateChanged.pipe().debounceTime(500).subscribe(() => {
            if(this.dataService.isLoading){
                this.loadingModal.open();
            } else {
                this.loadingModal.close();
            }
            this.changeDetectorRef.detectChanges();
            this.isLoading = this.dataService.isLoading;
            this.changeDetectorRef.detectChanges();
        });
    }

    public toggleLoggingView(): void {
        this.viewController.loggingOutputShown = !this.viewController.loggingOutputShown;
    }
}
