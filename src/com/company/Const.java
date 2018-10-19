package com.company;

import static com.company.Type.VALUE;

public class Const extends DMSNode {
    private Type type;
    Const(String name, Type type) {
        super(name);
        this.type = type;
    }

    public String getVal() {
        return this.getName();
    }

    public Type getType() {
        return this.type;
    }
}
