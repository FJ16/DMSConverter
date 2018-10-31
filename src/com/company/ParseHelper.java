package com.company;

public class ParseHelper {
    public boolean isComparison(Character input) {
        if (input == '<' || input == '>' || input == '=')
            return true;
        else return false;
    }

    public boolean isOperator(Character input) {
        if (input == '+' || input == '-' || input == '*' || input == '/')
            return true;
        else return false;
    }

    public String getCondSign(Character first, Character second) {
        if (isComparison(second)) {
            return "" + first + second;
        }
        return "" + first;
    }

    public int findSectionEnd(String str, int start, int end, char tar) {
        int mark = 0; // indicates if we met another '(' or not

        // should mark [ pr " ?
        while (start < end) {
            char cur = str.charAt(start);
            if (cur == '(') mark++;
            else if (cur == ')') mark--;

            if (cur == tar && mark == 0) return start;
            start++;
        }

        return start; // throw exceptions
    }

    public Function generalFuncHelper(Function funcObj, String funcBody, int numOfVar) {
        return null;
    }
}
