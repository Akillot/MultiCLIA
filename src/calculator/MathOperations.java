package calculator;

import settings.AppearanceSettings;

public class MathOperations {
    static double result;
    // Summation
    public static void sum(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        result = 0;
        for (String num : nums) {
            try {
                double numDouble = Double.parseDouble(num);
                result += numDouble;
            } catch (NumberFormatException e) {
                System.out.println(num + " is not a number");
                return;
            }
        }
        System.out.println("Answer: " + result + "\n" + AppearanceSettings.border + "\n");
    }

    // Subtraction
    public static void sub(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            System.out.println("No numbers to subtract.");
            return;
        }
        result = 0;
        try {
            result = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            System.out.println(nums[0] + " is not a number");
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                result -= numDouble;
            } catch (NumberFormatException e) {
                System.out.println(nums[i] + " is not a number");
            }
        }
        System.out.println("Answer: " + result + "\n" + AppearanceSettings.border + "\n");
    }

    // Multiplication
    public static void multi(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            System.out.println("No numbers to multiply.");
            return;
        }
        result = 1;
        for (String num : nums) {
            try {
                double numDouble = Double.parseDouble(num);
                result *= numDouble;
            } catch (NumberFormatException e) {
                System.out.println(num + " is not a number");
                return;
            }
        }
        System.out.println("Answer: " + result + "\n" + AppearanceSettings.border + "\n");
    }

    // Division
    public static void divide(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            System.out.println("No numbers to divide.");
            return;
        }
        result = 1;
        try {
            result = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            System.out.println(nums[0] + " is not a number");
            return;
        }
        //hello
        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                if (numDouble == 0) {
                    System.out.println("Division by zero detected. Aborting operation.");
                    return;
                }
                result /= numDouble;
            } catch (NumberFormatException e) {
                System.out.println(nums[i] + " is not a number");
                return;
            }
        }
        System.out.println("Answer: " + result + "\n" + AppearanceSettings.border + "\n");
    }

    // Power
    public static void pow(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            System.out.println("No numbers to process.");
            return;
        }
        result = 1;
        try {
            result = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            System.out.println(nums[0] + " is not a number");
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                result = Math.pow(result, numDouble);
            } catch (NumberFormatException e) {
                System.out.println(nums[i] + " is not a number");
                return;
            }
        }
        System.out.println("Answer: " + result + "\n" + AppearanceSettings.border + "\n");
    }
}