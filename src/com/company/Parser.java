package com.company;

import static com.company.Type.NUM;
import static com.company.Type.VALUE;

public class Parser {
    // private int index;
    private String Calculation;
    private ParseHelper helper;

    Parser(String input) {
        Calculation = input;
        helper = new ParseHelper();
    }

    // endIdx initially equals to Calculation.length();
    public DMSNode parse(int startIdx, int endIdx) {
        int preIdx = startIdx;
        for (int i = startIdx; i < endIdx; ++i) {
            // (, ), [, ], <>=, "" and ,
            char cur = Calculation.charAt(i);

            // encounter function
            if (cur == '(') {
               String namePartern = Calculation.substring(preIdx, i);

               if (namePartern.equals("if")) {
                   Condition ifFunc = new Condition();

                   // find sign
                   int j = ++i; // move i to the right of '(', make sure the first comparison mark is what we need
                   while (j < endIdx  && !helper.isComparison(Calculation.charAt(j))) {
                       j++;
                   }

                   int leftExpEnd = j;
                   String condSign = helper.getCondSign(Calculation.charAt(j), Calculation.charAt(j + 1));
                   // if out of bound, throw exception: invalid input form

                   if (helper.isComparison(Calculation.charAt(j + 1))) j++;
                   // match <=, >= and <>

                   Expression ifExp = new Expression(condSign);
                   ifExp.setLeftNode(parse(i, leftExpEnd)); //

                   int rightExpStart = ++j; // move j to the right of comparision sign;
                   j = helper.findSectionEnd(Calculation, j, endIdx, ',');
                   ifExp.setRightNode(parse(rightExpStart, j));

                   ifFunc.setExpression(ifExp);

                   // follow the result when the expression return true
                   int leftResultStartIdx = ++j;
                   j = helper.findSectionEnd(Calculation, j, endIdx, ',');
                   ifFunc.setTrueSide(parse(leftResultStartIdx, j));

                   // branch for false side, no need to certain the range, it's the subtree root node
                   int falseSideStart = ++j;
                   ifFunc.setFalseSide(parse(falseSideStart, endIdx));

                   return ifFunc;
               }

               //
               else if (namePartern.equals("map")) {
                   // implement map func
                   Condition mapFunc = new Condition();

                   int sectionStart = ++i;
                   int sectionEnd = sectionStart;
                   sectionEnd = helper.findSectionEnd(Calculation, sectionEnd, endIdx, ',');
                   Expression mapExp = new Expression("=");
                   mapExp.setLeftNode(parse(sectionStart, sectionEnd));

                   sectionStart = ++sectionEnd;
                   sectionEnd = helper.findSectionEnd(Calculation, sectionEnd, endIdx, ',');
                   mapExp.setRightNode(parse(sectionStart, sectionEnd));

                   mapFunc.setExpression(mapExp);

                   sectionStart = ++sectionEnd;
                   sectionEnd = helper.findSectionEnd(Calculation, sectionEnd, endIdx, ',');
                   mapFunc.setTrueSide(parse(sectionStart, sectionEnd));

                   sectionStart = ++sectionEnd;
                   mapFunc.setFalseSide(parse(sectionStart, endIdx));

                   return mapFunc;
               }

               // other functions
               else {

               }


            }

            // encounter variable
            else if (cur == '[') {
                int varNameStart = ++i;
                int varNameEnd = helper.findSectionEnd(Calculation, i, endIdx, ']');
                return new Variable(Calculation.substring(varNameStart, varNameEnd));
            }

            // encounter constant
            else if (cur == '"') {
                int consStart = ++i;
                int consEnd = helper.findSectionEnd(Calculation, i, endIdx, '"');
                String val = Calculation.substring(consStart, consEnd);
                return new Const(val, VALUE);
            }

            // deal with no significant mark

        }

        return new Const(Calculation.substring(startIdx, endIdx), NUM);
    }

    public String getCalculation() {
        return this.Calculation;
    }
}
