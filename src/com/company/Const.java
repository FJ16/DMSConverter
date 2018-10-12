package com.company;

import static com.company.Type.VALUE;

public class Const extends DMSNode {
    Const(String name) {
        super(name, VALUE);
    }

    public String getVal() {
        return this.getName();
    }

}
