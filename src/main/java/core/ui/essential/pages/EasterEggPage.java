package core.ui.essential.pages;

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.TextConfigs.*;
import static java.lang.System.out;

public class EasterEggPage {
    public static void displayEasterEgg() {
        Scanner scanner = new Scanner(System.in);
        insertControlChars('n', 1);
        message("Congratulation! You found the Easter egg - the" + getRandomColor()
                        + " MathCLIA" + getColor(layoutColor) + " - the concept before"
                        + getColor(mainColor) +" MultiCLIA" + getColor(layoutColor) + "!",
                layoutColor,getDefaultTextAlignment(),getDefaultDelay(),out::println);

        while (true) {
            try {
                out.println("MathCLIA");
                out.println("-------------------\nEnter your numbers.");
                out.println("-------------------\nUse a 'SPACE'\nTo split numbers.\n-------------------");
                String mathStatementString = scanner.nextLine();
                operationMenu();
                out.print("Your choice is: ");
                String operation = scanner.nextLine();
                out.println("-------------------");
                switch (operation) {
                    case "sum" -> sum(mathStatementString);
                    case "sub" -> sub(mathStatementString);
                    case "multi" -> multi(mathStatementString);

                    case "divide" -> divide(mathStatementString);
                    case "pow" -> pow(mathStatementString);
                    case "info" -> versionInfo();
                    case "exit" -> {
                        insertControlChars('n', 1);
                        return;
                    }
                    default -> out.println("Invalid operation.\n-------------------\n ");
                }
            } catch (Exception ex) {
                out.println("Error: " + ex.getMessage());
            }
        }
    }

    static double result;

    static void sum(@NotNull String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        result = 0;
        for (String num : nums) {
            try {
                double numDouble = Double.parseDouble(num);
                result += numDouble;
            } catch (NumberFormatException e) {
                out.println(num + " is not a number");
                return;
            }
        }
        out.println("Answer: " + result + "\n-------------------\n");
    }

    static void sub(@NotNull String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            out.println("No numbers to subtract.");
            return;
        }
        result = 0;
        try {
            result = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            out.println(nums[0] + " is not a number");
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                result -= numDouble;
            } catch (NumberFormatException e) {
                out.println(nums[i] + " is not a number");
            }
        }
        out.println("Answer: " + result + "\n-------------------\n");
    }

    static void multi(@NotNull String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            out.println("No numbers to multiply.");
            return;
        }
        result = 1;
        for (String num : nums) {
            try {
                double numDouble = Double.parseDouble(num);
                result *= numDouble;
            } catch (NumberFormatException e) {
                out.println(num + " is not a number");
                return;
            }
        }
        out.println("Answer: " + result + "\n-------------------\n");
    }

    static void divide(@NotNull String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            out.println("No numbers to divide.");
            return;
        }
        result = 1;
        try {
            result = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            out.println(nums[0] + " is not a number");
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                if (numDouble == 0) {
                    out.println("Division by zero detected. Aborting operation.");
                    return;
                }
                result /= numDouble;
            } catch (NumberFormatException e) {
                out.println(nums[i] + " is not a number");
                return;
            }
        }
        out.println("Answer: " + result + "\n-------------------\n");
    }

    static void pow(@NotNull String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            out.println("No numbers to process.");
            return;
        }
        result = 1;
        try {
            result = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            out.println(nums[0] + " is not a number");
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                result = Math.pow(result, numDouble);
            } catch (NumberFormatException e) {
                out.println(nums[i] + " is not a number");
                return;
            }
        }
        out.println("Answer: " + result + "\n-------------------\n");
    }

    static void operationMenu() {
        out.println("-------------------\nOperations:\n1. sum[+]\n2. sub[-]\n3. multi[*]\n4. divide[/]\n5. pow[^]\n6. info[?]\n7. exit\n-------------------");
    }

    static void versionInfo() {
        out.println("Current version:\n0.2.1");
    }
}
