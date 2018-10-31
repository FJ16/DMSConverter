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

        boolean pairCheck = false; // deal with redundant )
        int levelEndIdx = endIdx;

        for (int i = startIdx; i < endIdx; ++i) {
            // (, ), [, ], <>=, "" and ,
            char cur = Calculation.charAt(i);

            // encounter function
            if (cur == '(') {
               String namePartern = Calculation.substring(startIdx, i);
               pairCheck = true;

               if (namePartern.equals("if")) {
                   Condition ifFunc = new Condition();

                   int sectionStart = ++i;
                   int sectionEnd = sectionStart;
                   sectionEnd = helper.findSectionEnd(Calculation, sectionEnd, endIdx, ',');

                   ifFunc.setExpression(parseExpression(sectionStart, sectionEnd));

                   // follow the result when the expression return true
                   sectionStart = ++sectionEnd;
                   sectionEnd = helper.findSectionEnd(Calculation, sectionEnd, endIdx, ',');
                   ifFunc.setTrueSide(parse(sectionStart, sectionEnd));

                   // branch for false side, no need to certain the range, it's the subtree root node
                   sectionStart = ++sectionEnd;

                   // assume the if() have entire body from start to end
                   ifFunc.setFalseSide(parse(sectionStart, endIdx));
                   return ifFunc;
               }

               //
               else if (namePartern.equals("map")) {
                   // implement map func
                   Map mapFunc = new Map();

                   int sectionStart = ++i;
                   int sectionEnd = sectionStart;
                   sectionEnd = helper.findSectionEnd(Calculation, sectionEnd, endIdx, ',');
                   try {
                       Variable mapVar = (Variable) parse(sectionStart, sectionEnd);
                       mapFunc.setVariable(mapVar);
                   } catch (Exception e) {
                       System.out.println("Fail to get variable from map expression");
                       // throw and handle exception
                   }

                   while (sectionEnd < endIdx) {
                       DMSNode[] curPair = new DMSNode[2];
                       sectionStart = ++sectionEnd;
                       sectionEnd = helper.findSectionEnd(Calculation, sectionEnd, endIdx, ',');

                       if (sectionEnd == endIdx) {
                           sectionEnd--;
                           mapFunc.setEndCondition(parse(sectionStart, sectionEnd));
                           break;
                       }

                       curPair[0] = parse(sectionStart, sectionEnd);

                       sectionStart = ++sectionEnd;
                       sectionEnd = helper.findSectionEnd(Calculation, sectionEnd, endIdx, ',');
                       curPair[1] = parse(sectionStart, sectionEnd);
                       mapFunc.getConditions().add(curPair);
                   }


                   return mapFunc;
               }

               else if (namePartern.equals("inlist") || namePartern.equals("INLIST")) {
                   Function inList = new Function("inlist");

                   int sectionStart = ++i;
                   int sectionEnd = sectionStart;
                   sectionEnd = helper.findSectionEnd(Calculation, sectionEnd, endIdx, ',');
                   DMSNode queryVar = parse(sectionStart, sectionEnd);
                   String targetList = Calculation.substring(sectionEnd + 1, endIdx - 1);

                   inList.setVariable(queryVar);
                   inList.setFuncAttribute(targetList);
                   return inList;
               }

               // other functions
               else if (namePartern.equals("addyear")) {
                   Function addYear = new Function("addyear");
                   addYear.setFuncAttribute("yy"); // save the converted attribute

                   int sectionStart = ++i;
                   int sectionEnd = sectionStart;
                   sectionEnd = helper.findSectionEnd(Calculation, sectionEnd, endIdx, ',');
                   DMSNode varAdded = parse(sectionStart, sectionEnd);

                   sectionStart = ++sectionEnd;
                   sectionEnd = helper.findSectionEnd(Calculation, sectionEnd, endIdx, ')');
                   DMSNode addNum = parse(sectionStart, sectionEnd);

                   addYear.getSubFunc().add(addNum);
                   addYear.getSubFunc().add(varAdded);
                   return addYear;
               }

               else if (namePartern.equals("monthsbetween")) {
                   Function monthsBetween = new Function("monthsbetween");
                   monthsBetween.setFuncAttribute("m");

                   int sectionStart = ++i;
                   int sectionEnd = sectionStart;
                   sectionEnd = helper.findSectionEnd(Calculation, sectionEnd, endIdx, ',');
                   DMSNode dateNodeFirst = parse(sectionStart, sectionEnd);

                   sectionStart = ++sectionEnd;
                   sectionEnd = helper.findSectionEnd(Calculation, sectionEnd, endIdx, ')');
                   DMSNode dateNodeSecond = parse(sectionStart, sectionEnd);

                   monthsBetween.getSubFunc().add(dateNodeSecond);
                   monthsBetween.getSubFunc().add(dateNodeFirst);
                   return monthsBetween;
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
            else if (cur == ')' && !pairCheck) {
                levelEndIdx = i;
                pairCheck = true;
            }
        }

        return new Const(Calculation.substring(startIdx, levelEndIdx), NUM);
    }

    public DMSNode parseExpression(int start, int end) {
        int expOperatorIdx = -1;
        int funcMark = 0;

        // find comparision char first
        int sectionStart = start;
        int sectionEnd = start; // move i to the right of '(', make sure the first comparison mark is what we need

        // only same level comparision sign will be picked up
        while (sectionEnd < end && (!helper.isComparison(Calculation.charAt(sectionEnd)) || funcMark != 0)) {
            char cur = Calculation.charAt(sectionEnd);
            if (cur == '(') funcMark++;
            else if (cur == ')') funcMark--;

            else if (helper.isOperator(cur) && funcMark == 0) {
                expOperatorIdx = sectionEnd;
            }
            sectionEnd++;
        }

        if (sectionEnd < end) {
            String condSign = helper.getCondSign(Calculation.charAt(sectionEnd), Calculation.charAt(sectionEnd + 1));
            // if out of bound, throw exception: invalid input form

            Expression expNode = new Expression(condSign);

            if (expOperatorIdx == -1) {
                // no calculation in left side, only comparision
                expNode.setLeftNode(parse(sectionStart, sectionEnd));

                if (helper.isComparison(Calculation.charAt(sectionEnd + 1))) sectionEnd++;
                // match <=, >= and <>

                // right side next
                sectionStart = ++sectionEnd;
                expNode.setRightNode(parse(sectionStart, end));
                return expNode;
            } else {
                // includes both calculation and comparision, calculation only appears in left part of comparision
                expNode.setLeftNode(parseExpression(sectionStart, sectionEnd));

                if (helper.isComparison(Calculation.charAt(sectionEnd + 1))) sectionEnd++;
                // match <=, >= and <>

                // right side next
                sectionStart = ++sectionEnd;
                expNode.setRightNode(parse(sectionStart, end));
                return expNode;
            }

        } else {
            if (expOperatorIdx == -1) {
                // only includes whole func like inlist()
                return parse(start, end);
            } else {
                // has calculation but no comparision
                Expression calc = new Expression(Calculation.charAt(expOperatorIdx) + "");
                calc.setLeftNode(parse(start, expOperatorIdx));
                calc.setRightNode(parse(expOperatorIdx + 1, end));
                return calc;
            }
        }

    }

    public String getCalculation() {
        return this.Calculation;
    }
}
