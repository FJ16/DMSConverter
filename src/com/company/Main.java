package com.company;

public class Main {

    public static void main(String[] args) {
	    // write your code here
        String testInput1 = "map([AccrualBasis],\"30/360\",\"B\",\"365/360\",\"D\",\"365/365\",\"F\",\"A\")";
        String testInput2 = "if([Payment Frequency]=0,0,if([Payment Frequency]=1,1,if([Payment Frequency]=3,3,if([Payment Frequency]=6,6,if([Payment Frequency]=12,12,12)))))";
        String testInput3 = "[col1]";
        String testInput4 = "\"N\"";
        String testInput5 = "2";
        String testInput6 = "map([col1],\"Y\",1,7)";
        String testInput7 = "if([col1]=\"Fixed\",\"F\",\"A\")";
        String testInput8 = "if([col1]=\"Fixed\",addyear([col2],10),[col3])";

        try {
            Parser test = new Parser(testInput8);
            DMSNode result = test.parse(0,testInput8.length());
            System.out.println(test.getCalculation());
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
    }

}


