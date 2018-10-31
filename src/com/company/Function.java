package com.company;

import java.util.ArrayList;
import java.util.List;
import static com.company.Type.FUNC;


public class Function extends DMSNode {
    private String funcAttribute;
    private DMSNode variable;
    private List<DMSNode> subFuncs;

    Function(String name) {
        super(name, FUNC);
        funcAttribute = "";
        subFuncs = new ArrayList<>();
    }

    public String getFuncAttribute() {
        return funcAttribute;
    }

    public DMSNode getVariable() {
        return variable;
    }

    public List<DMSNode> getSubFunc() {
        return subFuncs;
    }

    public void setFuncAttribute(String str) {
        funcAttribute = str;
    }

    public void setVariable(DMSNode var) {
        variable = var;
    }
}
