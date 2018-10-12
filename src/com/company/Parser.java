package com.company;
import com.company.ParseHelper;

public class Parser {
    private String Calculation;
    // private int index;
    // private int preIdx;
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
                   // match <= and >=

                   Expression ifExp = new Expression(condSign);
                   ifExp.setLeftNode(parse(i, leftExpEnd)); //

                   int rightExpStart = ++j; // move j to the right of comparision sign;
                   j = helper.findSectionEnd(Calculation, j, endIdx, ',');
                   ifExp.setRightNode(parse(rightExpStart, j));

                   ifFunc.setTrueSide(ifExp);

                   // follow the result when the expression return true
                   int leftResultStartIdx = ++j;
                   j = helper.findSectionEnd(Calculation, j, endIdx, ',');
                   ifFunc.setResultIfTrue(parse(leftResultStartIdx, j));

                   // branch for false side, no need to certain the range, it's the subtree root node
                   int falseSideStart = ++j;
                   ifFunc.setFalseSide(parse(falseSideStart, endIdx));

                   return ifFunc;
               }
               else if (namePartern.equals("map")) {
                    // implement map func
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
                return new Const(val);
            }

        }

        return null;
    }
}
