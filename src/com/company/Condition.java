package com.company;

import java.util.ArrayList;
import java.util.List;

import static com.company.Type.COND;

public class Condition extends DMSNode {
    private String condSign;
    private DMSNode expression;
    private DMSNode trueSide;
    private DMSNode falseSide;

    Condition() {
        super("if", COND);
    }

    public String getCondSign() {
        return condSign;
    }

    public DMSNode getTrueSide() {
        return trueSide;
    }

    public DMSNode getFalseSide() {
        return falseSide;
    }

    public DMSNode getExpression() {
        return expression;
    }

    public void setCondSign(String condSign) {
        this.condSign = condSign;
    }

    public void setTrueSide(DMSNode trueSide) {
        this.trueSide = trueSide;
    }

    public void setFalseSide(DMSNode falseSide) {
        this.falseSide = falseSide;
    }

    public void setExpression(DMSNode expression) {
        this.expression = expression;
    }
}
