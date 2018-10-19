package com.company;

public class Main {

    public static void main(String[] args) {
	    // write your code here
        String testInput = "if([Payment Frequency]=0,0,if([Payment Frequency]=1,1,if([Payment Frequency]=3,3,if([Payment Frequency]=6,6,if([Payment Frequency]=12,12,12)))))";

        try {
            Parser test = new Parser(testInput);
            DMSNode result = test.parse(0,testInput.length());
            System.out.println(test.getCalculation());
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
    }

}


