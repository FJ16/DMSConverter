package com.company;

public class Expression extends DMSNode {
    private DMSNode leftNode;
    private DMSNode rightNode;

    Expression(String name) {
        super(name);
    }

    public DMSNode getLeftNode() {
        return leftNode;
    }

    public DMSNode getRightNode() {
        return rightNode;
    }

    public void setLeftNode(DMSNode leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(DMSNode rightNode) {
        this.rightNode = rightNode;
    }
}
