package com.company;

import java.util.ArrayList;
import java.util.List;
import static com.company.Type.MAP;


public class Map extends DMSNode {
    private DMSNode variable;
    private List<DMSNode[]> conditions;
    private DMSNode endCondition;

    Map() {
        super("map", MAP);
        conditions = new ArrayList<>();
    }

    public DMSNode getVariable() {
        // if not null
        return variable;
    }

    public DMSNode getEndCondition() {
        return endCondition;
    }

    public List<DMSNode[]> getConditions() {
        return conditions;
    }

    public void setVariable(Variable var) {
        variable = var;
    }

    public void setEndCondition(DMSNode endVar) {
        endCondition = endVar;
    }
}
