package com.specmate.test.integration;

import org.json.JSONArray;
import org.junit.Test;

import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
import com.specmate.model.requirements.RequirementsFactory;

/**
 * Class to test the CEG-Model generation for english sentences.
 *
 */
public class ModelGenerationTestEn extends ModelGenerationTestBase {

	public ModelGenerationTestEn() throws Exception {
		super();
	}

	@Test
	public void testModelGenerationEn1_Or() {
		String text = "If Specmate detects an error or the user has no login, Specmate shows a warning window and makes a sound.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "detects an error", NodeType.AND);
		CEGNode node2 = createNode(model, "the user", "has a login", NodeType.AND);
		CEGNode node3 = createNode(model, "Specmate", "shows a warning window", NodeType.OR);
		CEGNode node4 = createNode(model, "Specmate", "makes a sound", NodeType.OR);

		createConnection(model, node1, node3, false);
		createConnection(model, node2, node3, true);
		createConnection(model, node1, node4, false);
		createConnection(model, node2, node4, true);

		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEn2_Passive() {
		String text = "If an error is detected or the user has no login, a warning window is shown and a sound is emitted.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "an error", "is detected", NodeType.AND);
		CEGNode node2 = createNode(model, "the user", "has a login", NodeType.AND);
		CEGNode node3 = createNode(model, "a warning window", "is shown", NodeType.OR);
		CEGNode node4 = createNode(model, "a sound", "is emitted", NodeType.OR);

		createConnection(model, node1, node3, false);
		createConnection(model, node2, node3, true);
		createConnection(model, node1, node4, false);
		createConnection(model, node2, node4, true);

		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEn3_AndOr() {
		String text = "If the user has no login and login is needed or an error is detected, a warning window is shown and a signal is emitted.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "an error", "is detected", NodeType.AND);
		CEGNode node2 = createNode(model, "the user", "has a login", NodeType.AND);
		CEGNode node3 = createNode(model, "login", "is needed", NodeType.AND);
		CEGNode node4 = createNode(model, "the user", "has no login and login is needed", NodeType.AND);
		CEGNode node5 = createNode(model, "a warning window", "is shown", NodeType.OR);
		CEGNode node6 = createNode(model, "a signal", "is emitted", NodeType.OR);

		createConnection(model, node2, node4, true);
		createConnection(model, node3, node4, false);
		createConnection(model, node4, node5, false);
		createConnection(model, node4, node6, false);
		createConnection(model, node1, node5, false);
		createConnection(model, node1, node6, false);

		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEn4_SpecmateExamples() {
		String text = "When the user selects the option to create a process model in the Process Models section of the Requirements Overview, an empty process model is displayed in the Process Model Editor.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user",
				"selects the option to create a process model in the Process Models section of the Requirements Overview",
				NodeType.AND);
		CEGNode node2 = createNode(model, "an empty process model", "is displayed in the Process Model Editor",
				NodeType.AND);

		createConnection(model, node1, node2, false);

		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN5_pattern1_1() {
		String text = "If Specmate detects an error, Specmate shows a warning window.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "detects an error", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "shows a warning window", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN6_pattern1_2() {
		String text = "Specmate shows a warning window if Specmate detects an error.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "shows a warning window", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "detects an error", NodeType.AND);
		createConnection(model, node2, node1, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN7_pattern1_3() {
		String text = "If Specmate detects an error, then Specmate shows a warning window.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "detects an error", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "shows a warning window", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN8_pattern2_1() {
		String text = "When Specmate detects an error, Specmate shows a warning window.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "detects an error", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "shows a warning window", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN9_pattern2_2() {
		String text = "Specmate shows a warning window when Specmate detects an error.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "shows a warning window", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "detects an error", NodeType.AND);
		createConnection(model, node2, node1, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN10_pattern2_3() {
		String text = "When Specmate detects an error then Specmate shows a warning window.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "detects an error", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "shows a warning window", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN11_and_1() {
		String text = "If the user presses the button and the model is not saved, Specmate saves the model.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "presses the button", NodeType.AND);
		CEGNode node2 = createNode(model, "the model", "is saved", NodeType.AND);
		CEGNode node3 = createNode(model, "Specmate", "saves the model", NodeType.AND);
		createConnection(model, node1, node3, false);
		createConnection(model, node2, node3, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN12_or_1() {
		String text = "If the user presses the button or the model is unsaved, Specmate saves the model.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "presses the button", NodeType.AND);
		CEGNode node2 = createNode(model, "the model", "is unsaved", NodeType.AND);
		CEGNode node3 = createNode(model, "Specmate", "saves the model", NodeType.OR);
		createConnection(model, node1, node3, false);
		createConnection(model, node2, node3, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN13_or_2() {
		String text = "If the model contains an edge, a node or a decision-node, Specmate displays the details at the right.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the model", "contains an edge", NodeType.AND);
		CEGNode node2 = createNode(model, "the model", "contains a node", NodeType.AND);
		CEGNode node3 = createNode(model, "the model", "contains a decision-node", NodeType.AND);
		CEGNode node4 = createNode(model, "Specmate", "displays the details at the right", NodeType.OR);
		createConnection(model, node1, node4, false);
		createConnection(model, node2, node4, false);
		createConnection(model, node3, node4, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN14_and_or_1() {
		String text = "If the user clicks the button or the user hits the Enter-key and unsaved changes exists, Specmate shows a confirmation window.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "clicks the button", NodeType.AND);
		CEGNode node2 = createNode(model, "the user", "hits the Enter-key", NodeType.AND);
		CEGNode node3 = createNode(model, "unsaved changes", "exists", NodeType.AND);
		CEGNode node4 = createNode(model, "Specmate", "shows a confimation window", NodeType.AND);
		CEGNode node5 = createNode(model, "the user", "clicks the button or the user hits the Enter-key", NodeType.OR);
		createConnection(model, node1, node5, false);
		createConnection(model, node2, node5, false);
		createConnection(model, node3, node4, false);
		createConnection(model, node5, node4, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN15_and_2() {
		String text = "If the user hits the button, Specmate saves the changes and Specmate opens a new window.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "hits the button", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "saves the changes", NodeType.AND);
		CEGNode node3 = createNode(model, "Specmate", "opens a new window", NodeType.AND);
		createConnection(model, node1, node2, false);
		createConnection(model, node1, node3, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN16_and_3() {
		String text = "If the user clicks the button, Specmate displays a window and Specmate saves the changes and Specmate reload the page.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "clicks the button", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "displays a window", NodeType.AND);
		CEGNode node3 = createNode(model, "Specmate", "saves the changes", NodeType.AND);
		CEGNode node4 = createNode(model, "Specmate", "reload the page", NodeType.AND);
		createConnection(model, node1, node2, false);
		createConnection(model, node1, node3, false);
		createConnection(model, node1, node4, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN17_and_4() {
		String text = "If the user clicks the button, Specmate displays a window, Specmate saves the changes and Specmate reload the page.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "clicks the button", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "displays a window", NodeType.AND);
		CEGNode node3 = createNode(model, "Specmate", "saves the changes", NodeType.AND);
		CEGNode node4 = createNode(model, "Specmate", "reload the page", NodeType.AND);
		createConnection(model, node1, node2, false);
		createConnection(model, node1, node3, false);
		createConnection(model, node1, node4, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN18_or_3() {
		String text = "If the user clicks the button or hits the Enter-key, Specmate loads the model.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "clicks the button", NodeType.AND);
		CEGNode node2 = createNode(model, "the user", "hits the Enter-key", NodeType.AND);
		CEGNode node3 = createNode(model, "Specmate", "loads the model", NodeType.OR);
		createConnection(model, node1, node3, false);
		createConnection(model, node2, node3, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN19_or_4() {
		String text = "If the model has only one node or one edge, the save-button is not visible.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the model", "has only one node", NodeType.AND);
		CEGNode node2 = createNode(model, "the model", "has only one edge", NodeType.AND);
		CEGNode node3 = createNode(model, "the save-button", "is not visible", NodeType.OR);
		createConnection(model, node1, node3, false);
		createConnection(model, node2, node3, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN20_or_pronoun() {
		String text = "If a node or an edge is selected, it can be deleted. ";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "a node", "is selected", NodeType.AND);
		CEGNode node2 = createNode(model, "an edge", "is selected", NodeType.AND);
		CEGNode node3 = createNode(model, "the node or the egde", "can be deleted", NodeType.OR);
		createConnection(model, node1, node3, false);
		createConnection(model, node2, node3, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN21_pronoun() {
		String text = "If the user clicks on the model, it is loaded into the editor.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "clicks on the model", NodeType.AND);
		CEGNode node2 = createNode(model, "the model", "is loaded into the editor", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN22_passiv_1() {
		String text = "Clicking on the link, starts the editor.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the link", "is clicked", NodeType.AND);
		CEGNode node2 = createNode(model, "the editor", "starts", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN23_passiv_not() {
		String text = "If an error is detected or the user has no login, a warning window is shown and a sound is emitted.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "an error", "is detected", NodeType.AND);
		CEGNode node2 = createNode(model, "the user", "has login", NodeType.AND);
		CEGNode node3 = createNode(model, "a warning window", "is shown", NodeType.OR);
		CEGNode node4 = createNode(model, "a sound", "is emitted", NodeType.OR);
		createConnection(model, node1, node3, false);
		createConnection(model, node1, node4, false);
		createConnection(model, node2, node3, true);
		createConnection(model, node2, node4, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN24_passiv_2() {
		String text = "If the link is clicked by the user, Specmate opens a new window.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the link", "is clicked by the user", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "opens a new window", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN25_passiv_3() {
		String text = "If the link is clicked by the user, Specmate opens a new window.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "clicks the link", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "opens a new window", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN26_not_1() {
		String text = "If changes are not saved, Specmate opens a confirmation window.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "changes", "are saved", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "opens a confirmation window", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN27_not_2() {
		String text = "If changes aren't saved, Specmate opens a confirmation window.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "changes", "are saved", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "opens a confirmation window", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN28_pattern3_1() {
		String text = "Specmate opens the model because the user clicks the link.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "clicks the link", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "opens the model", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN29_pattern3_2() {
		String text = "Because the user clicks the link, Specmate opens the model.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "clicks the link", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "opens the model", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN30_pattern4_1() {
		String text = "The user clicks the button for this reason Specmate opens the model.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "clicks the button", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "opens the model", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN31_pattern4_2() {
		String text = "The user enters his login data. For this reason, Specmate logs the user in.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "enters his login data", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "logs the user in", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN32_pattern5_1() {
		String text = "Specmate shows the error window as a result of invalid login data";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "invalid login data", "exists", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "shows the error window", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN33_pattern5_2() {
		String text = "The user presses the button. As a result, Specmate shows a window.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "presses the button", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "shows a window", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN34_pattern6_1() {
		String text = "Specmate shows a warning window due to invalid login data. ";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "invalid login data", "exists", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "shows a warning window", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN35_pattern6_2() {
		String text = "Due to invalid login data, Specmate shows a warning window.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "invalid login data", "exists", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "shows a warning window", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN36_pattern7_1() {
		String text = "Specmate shows a warning window owing to invalid login data.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "invalid login data", "exists", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "shows a warning window", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN37_pattern7_2() {
		String text = "Owing to invalid login data, Specmate shows a warning window.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "invalid login data", "exists", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "shows a warning window", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN38_pattern8_1() {
		String text = "Specmate saves the model provided that the model is correct.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the model", "is correct", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "saves the model", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN39_pattern8_2() {
		String text = "Provided that the model is correct, Specmate saves the model.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the model", "has errors", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "saves the model", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN40_not_3() {
		String text = "Provided that the model has no errors, Specmate saves the model.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the model", "has errors", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "saves the model", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN41_pattern9() {
		String text = "The problem has something to do with the bug.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the bug", "exists", NodeType.AND);
		CEGNode node2 = createNode(model, "a problem", "occurs", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN42_pattern10() {
		String text = "The problem has a lot to do with the bug.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the bug", "exists", NodeType.AND);
		CEGNode node2 = createNode(model, "a problem", "occurs", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN43_pattern11() {
		String text = "The error occurs so that Specmate crashes.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the error", "occurs", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "crashes", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN44_pattern12_1() {
		String text = "The user clicks the link in order that Specmate displays the model.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "clicks the link", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "displays the model", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN45_pattern12_2() {
		String text = "In order that Specmate opens the model, the user must click the link.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "must click the link", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "opens the model", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN46_pattern13_1() {
		String text = "Specmate saves the model although the model is not valid.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the model", "is valid", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "saves the model", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN47_pattern13_2() {
		String text = "Although the model is not valid, Specmate saves the model.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the model", "is valid", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "saves the model", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN48_pattern14_1() {
		String text = "Specmate saves the model even though the model is not valid.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "saves the model", NodeType.AND);
		CEGNode node2 = createNode(model, "the model", "is valid", NodeType.AND);
		createConnection(model, node2, node1, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN49_pattern14_2() {
		String text = "Even though the model is not valid, Specmate saves the model.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the model", "is valid", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "saves the model", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN50_pattern15_1() {
		String text = "Specmate loads the model, in the case that the user presses the button.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "presses the button", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "loads the model", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN51_pattern15_2() {
		String text = "In the case that the user presses the button, Specmate loads the model.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "presses the button", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "loads the model", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN52_pattern16_1() {
		String text = "Specmate loads the model on condition that the user presses the button.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "loads the model", NodeType.AND);
		CEGNode node2 = createNode(model, "the user", "presses the button", NodeType.AND);
		createConnection(model, node2, node1, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN53_pattern16_2() {
		String text = "On condition that the user presses the button, Specmate loads the model.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the user", "presses the button", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "loads the model", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN54_pattern17_1() {
		String text = "A local version is loaded, supposing that the connection fails.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the connection", "fails", NodeType.AND);
		CEGNode node2 = createNode(model, "a local version", "is loaded", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationEN55_pattern17_2() {
		String text = "Supposing that the connection fails, a local version is loaded.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "the connection", "fails", NodeType.AND);
		CEGNode node2 = createNode(model, "a local version", "is loaded", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

}
