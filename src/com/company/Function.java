package com.company;

import java.util.ArrayList;
import java.util.List;
import static com.company.Type.FUNC;


public class Function extends DMSNode {
    private String funcBody;
    private List<DMSNode> subFuncs;

    Function(String name) {
        super(name, FUNC);
        funcBody = "";
        subFuncs = new ArrayList<>();
    }

    public String getFuncBody() {
        return funcBody;
    }
}
