package com.company;

public class DMSNode {
    private String name;
    private Type type;

    DMSNode(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    DMSNode(String name) {
        this.name = name;
        type = null;
    }

    DMSNode() {};

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}
