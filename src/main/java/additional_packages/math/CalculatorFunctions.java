package additional_packages.math;

import static ui.layout.ColorWork.*;
import static ui.layout.TextWork.contentAlignment;
import static ui.layout.ThemesWork.displayErrorAscii;

public class CalculatorFunctions {
    static double result;

    public static void summationOperation(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        result = 0;
        for (String num : nums) {
            try {
                double numDouble = Double.parseDouble(num);
                result += numDouble;
            } catch (NumberFormatException e) {
                displayErrorAscii();
                displayColorMessage(num + " is not a number", "red", 0);
                return;
            }
        }
        displayAnswer(result);
    }

    public static void subtractionOperation(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            displayErrorAscii();
            displayColorMessage("No numbers to subtract", "red", 0);
            return;
        }
        result = 0;
        try {
            result = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            displayErrorAscii();
            displayColorMessage(nums[0] + " is not a number", "red", 0);
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                result -= numDouble;
            } catch (NumberFormatException e) {
                displayErrorAscii();
                displayColorMessage(nums[i] + " is not a number", "red", 0);
            }
        }
        displayAnswer(result);
    }

    public static void multiplyingOperation(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            displayErrorAscii();
            displayColorMessage("No numbers to multiply", "red", 0);
            return;
        }
        result = 1;
        for (String num : nums) {
            try {
                double numDouble = Double.parseDouble(num);
                result *= numDouble;
            } catch (NumberFormatException e) {
                displayErrorAscii();
                displayColorMessage(num + " is not a number", "red", 0);
                return;
            }
        }
        displayAnswer(result);
    }

    public static void dividingOperation(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            displayErrorAscii();
            displayColorMessage("No numbers to divide", "red", 0);
            return;
        }
        result = 1;
        try {
            result = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            displayErrorAscii();
            displayColorMessage(nums[0] + " is not a number", "red", 0);
            return;
        }

        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                if (numDouble == 0) {
                    displayErrorAscii();
                    displayColorMessage("Division by zero detected.\nAborting operation", "red", 0);
                    return;
                }
                result /= numDouble;
            } catch (NumberFormatException e) {
                displayErrorAscii();
                displayColorMessage(nums[i] + " is not a number", "red", 0);
                return;
            }
        }
        displayAnswer(result);
    }

    public static void powerOperation(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            displayErrorAscii();
            displayColorMessage("No numbers to process", "red", 0);
            return;
        }
        result = 1;
        try {
            result = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            displayErrorAscii();
            displayColorMessage(nums[0] + " is not a number", "red", 0);
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                result = Math.pow(result, numDouble);
            } catch (NumberFormatException e) {
                displayErrorAscii();
                displayColorMessage(nums[i] + " is not a number", "red", 0);
                return;
            }
        }
        displayAnswer(result);
    }

    public static void displayAnswer(double answer) {
        System.out.println(contentAlignment(18) + "Answer: " + answer);
    }

    public static void displayCalculatorOperationsList() {
        displayColorMessage("Operations:", "white", 0);
        displayColorMessage("· sum[+]", "white", 58);
        displayColorMessage("· sub[-]", "white", 58);
        displayColorMessage("· multi[*]", "white", 58);
        displayColorMessage("· div[/]", "white", 58);
        displayColorMessage("· pow[^]", "white", 58);
        System.out.println(contentAlignment(58) + "· " + BOLD + RED + "exit" + RESET + BOLD + WHITE + "[x]" + RESET);
    }
}