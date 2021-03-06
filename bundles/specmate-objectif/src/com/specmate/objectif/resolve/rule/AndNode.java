package com.specmate.objectif.resolve.rule;

public class AndNode extends ObjectifNode {
	private ObjectifNode leftNode;
	private ObjectifNode rightNode;
	
	public AndNode(ObjectifNode left, ObjectifNode right) {
		this.leftNode = left;
		this.rightNode = right;
	}
	
	@Override
	void generateCEG() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String toString() {
		return "("+this.leftNode+" && "+this.rightNode+")";
	}

	public ObjectifNode getLeft() {
		return this.leftNode;
	}
	
	public ObjectifNode getRight() {
		return this.rightNode;
	}
}
