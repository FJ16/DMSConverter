package com.company;

import static com.company.Type.VAR;

public class Variable extends DMSNode {
    private DMSNode var;

    Variable(String name) {
        super(name, VAR);
    }

    public DMSNode getVar() {
        return var;
    }
}
