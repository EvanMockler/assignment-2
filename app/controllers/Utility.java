package controllers;

import models.Assessment;
import models.Member;

public class Utility {

    public static double calculateBMI(Assessment assessment, Member member) {
        double latestWeight = assessment.getWeight();

        double num = 100 * latestWeight / (member.getHeight() * member.getHeight());
        num = (int) num;
        num = num / 100;
        return num;
    }

    public static String determineBMICategory(double bmiValue) {

        if (bmiValue < 16) {
            return "SEVERELY UNDERWEIGHT";
        } else if (bmiValue >= 16 && bmiValue < 18.5) {
            return "UNDERWEIGHT";
        } else if (bmiValue >= 18.5 && bmiValue < 25) {
            return "NORMAL";
        } else if (bmiValue >= 25 && bmiValue < 30) {
            return "OVERWEIGHT";
        } else if (bmiValue >= 30 && bmiValue < 35) {
            return "MODERATELY OBESE";
        } else if (bmiValue >= 35)  {

            return "SEVERELY OBESE";
        }

        return "";
    }


    public static boolean isIdealBodyWeight(Member member, Assessment assessment) {

        boolean isIdeal = true;
        double idealWeightMale;
        double idealWeightFemale;
        double height = member.getHeight()*39.37;

        if (height >= 60) {
            idealWeightMale = 50.0 + (2.3 * (height-60));
            idealWeightFemale = 45.5 +( 2.3 * (height-60));
        } else {
            idealWeightMale = 50.0;
            idealWeightFemale = 45.5;
        }

        if (member.gender.equals("Male")) {
            if (assessment.getWeight() >= idealWeightMale + 2) {
                isIdeal = false;
            } else if (assessment.getWeight() <= idealWeightMale - 2) {
                isIdeal = false;
            }

        } else if (member.gender.equals("Female")) {
            if (assessment.getWeight() >= idealWeightFemale + 2) {
                isIdeal = false;
            } else if (assessment.getWeight() <= idealWeightFemale - 2) {
                isIdeal = false;
            }
        } else if (member.gender.equals("")) {
            if (assessment.getWeight() >= idealWeightFemale + 2) {
                isIdeal = false;
            } else if ( assessment.getWeight() <= idealWeightFemale - 2) {
                isIdeal = false;
            }
        }
        return isIdeal;
    }



}
