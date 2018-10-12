package com.company;

import java.util.ArrayList;
import java.util.List;
import static com.company.Type.MAP;


public class Map extends DMSNode {
    private DMSNode variable;
    private List<DMSNode> conditions;

    Map() {
        super("map", MAP);
        conditions = new ArrayList<>();
    }

    public DMSNode getVariable() {
        // if not null
        return variable;
    }

    public List<DMSNode> getConditions() {
        return conditions;
    }
}
