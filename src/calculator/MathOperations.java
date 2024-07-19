package calculator;

import layout.UserInterface;

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
                UserInterface.displayError(num + " is not a number");
                return;
            }
        }
        showAnswer(result);
    }

    // Subtraction
    public static void sub(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {

            UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);
            UserInterface.displayError("No numbers to subtract");
            return;
        }
        result = 0;
        try {
            result = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            UserInterface.displayError(nums[0] + " is not a number");
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                result -= numDouble;
            } catch (NumberFormatException e) {
                UserInterface.displayError(nums[i] + " is not a number");
            }
        }
        showAnswer(result);
    }

    // Multiplication
    public static void multi(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            UserInterface.displayError("No numbers to multiply");
            return;
        }
        result = 1;
        for (String num : nums) {
            try {
                double numDouble = Double.parseDouble(num);
                result *= numDouble;
            } catch (NumberFormatException e) {
                UserInterface.displayError(num + " is not a number");
                return;
            }
        }
        showAnswer(result);
    }

    // Division
    public static void divide(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            UserInterface.displayError("No numbers to divide");
            return;
        }
        result = 1;
        try {
            result = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            UserInterface.displayError(nums[0] + " is not a number");
            return;
        }

        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                if (numDouble == 0) {
                    UserInterface.displayError("Division by zero detected.\nAborting operation");
                    return;
                }
                result /= numDouble;
            } catch (NumberFormatException e) {
                UserInterface.displayError(nums[i] + " is not a number");
                return;
            }
        }
        showAnswer(result);
    }

    // Power
    public static void pow(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            UserInterface.displayError("No numbers to process");
            return;
        }
        result = 1;
        try {
            result = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            UserInterface.displayError(nums[0] + " is not a number");
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                result = Math.pow(result, numDouble);
            } catch (NumberFormatException e) {
                UserInterface.displayError(nums[i] + " is not a number");
                return;
            }
        }
        showAnswer(result);
    }

    public static void showAnswer(double answer) {
        UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);
        System.out.println(UserInterface.centeringFunction(18) + "Answer: " + answer);
        UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);
    }
}