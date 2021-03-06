package com.specmate.testspecification.test;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.specmate.model.testspecification.ParameterAssignment;
import com.specmate.model.testspecification.ParameterType;
import com.specmate.model.testspecification.TestCase;
import com.specmate.model.testspecification.TestParameter;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestSpecificationSkeleton;
import com.specmate.model.testspecification.TestspecificationFactory;
import com.specmate.testspecification.internal.exporters.CSVTestSpecificationExporter;
import com.specmate.testspecification.internal.exporters.JavaTestSpecificationExporter;
import com.specmate.testspecification.internal.exporters.JavascriptTestSpecificationExporter;

public class TestExportTestSpecification {
	@Test
	public void testJavaExport() {
		TestSpecification ts = getTestSpecification();

		JavaTestSpecificationExporter skel = new JavaTestSpecificationExporter();
		TestSpecificationSkeleton result = skel.generate(ts);
		String code = result.getCode();

		Assert.assertTrue(code.contains("public void TSTest___in1__in11___in2_____out1__out11___out2__out12()"));
		Assert.assertTrue(code.contains("public void TSTest___in1__in21___in2__in22___out1__out21___out2__out22()"));
	}

	@Test
	public void testJavascriptExport() {
		TestSpecification ts = getTestSpecification();

		JavascriptTestSpecificationExporter skel = new JavascriptTestSpecificationExporter();
		TestSpecificationSkeleton result = skel.generate(ts);
		String code = result.getCode();

		Assert.assertTrue(code.contains("	it('TS___in1__in11___in2_____out1__out11___out2__out12', () => {\n"
				+ "		throw new Error('not implemented yet');\n" + "	});"));
		Assert.assertTrue(code.contains("it('TS___in1__in21___in2__in22___out1__out21___out2__out22', () => {\n"
				+ "		throw new Error('not implemented yet');\n" + "	});"));
	}

	@Test
	public void testCSVExport() {
		TestSpecification ts = getTestSpecification();

		CSVTestSpecificationExporter skel = new CSVTestSpecificationExporter();
		TestSpecificationSkeleton result = skel.generate(ts);
		String code = result.getCode();

		Assert.assertTrue(code.contains("\"TC\";\"INPUT - in1\";\"INPUT - in2\";\"OUTPUT - out1\";\"OUTPUT - out2\""));
		Assert.assertTrue(code.contains("tc1;\"in11\";;\"out11\";\"out12\""));
		Assert.assertTrue(code.contains("tc2;\"in21\";\"in22\";\"out21\";\"out22\""));

	}

	private TestSpecification getTestSpecification() {
		TestspecificationFactory f = TestspecificationFactory.eINSTANCE;
		TestSpecification ts = f.createTestSpecification();
		ts.setName("TS");

		TestParameter input1 = f.createTestParameter();
		input1.setId("in1");
		input1.setName("in1");
		input1.setType(ParameterType.INPUT);

		TestParameter input2 = f.createTestParameter();
		input2.setId("in2");
		input2.setName("in2");
		input2.setType(ParameterType.INPUT);

		TestParameter output1 = f.createTestParameter();
		output1.setId("out1");
		output1.setName("out1");
		output1.setType(ParameterType.OUTPUT);

		TestParameter output2 = f.createTestParameter();
		output2.setId("out2");
		output2.setName("out2");
		output2.setType(ParameterType.OUTPUT);

		ts.getContents().addAll(Arrays.asList(input1, input2, output1, output2));

		TestCase tc1 = f.createTestCase();
		tc1.setId("tc1");
		tc1.setName("tc1");

		ParameterAssignment assIn11 = f.createParameterAssignment();
		assIn11.setParameter(input1);
		assIn11.setCondition("in11");

		// empty input assignment
		ParameterAssignment assIn12 = f.createParameterAssignment();
		assIn12.setParameter(input2);
		assIn12.setCondition("");

		ParameterAssignment assOut11 = f.createParameterAssignment();
		assOut11.setParameter(output1);
		assOut11.setCondition("out11");

		ParameterAssignment assOut12 = f.createParameterAssignment();
		assOut12.setParameter(output2);
		assOut12.setCondition("out12");
		tc1.getContents().addAll(Arrays.asList(assIn11, assIn12, assOut11, assOut12));

		TestCase tc2 = f.createTestCase();
		tc2.setId("tc2");
		tc2.setName("tc2");

		ParameterAssignment assIn21 = f.createParameterAssignment();
		assIn21.setParameter(input1);
		assIn21.setCondition("in21");

		ParameterAssignment assIn22 = f.createParameterAssignment();
		assIn22.setParameter(input2);
		assIn22.setCondition("in22");

		ParameterAssignment assOut21 = f.createParameterAssignment();
		assOut21.setParameter(output1);
		assOut21.setCondition("out21");

		ParameterAssignment assOut22 = f.createParameterAssignment();
		assOut22.setParameter(output2);
		assOut22.setCondition("out22");
		tc2.getContents().addAll(Arrays.asList(assIn21, assIn22, assOut21, assOut22));

		ts.getContents().addAll(Arrays.asList(tc1, tc2));
		return ts;
	}
}