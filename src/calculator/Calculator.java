package calculator;

import layout.UserInterface;
import settings.AppearanceSettings;

import java.util.Scanner;

import static settings.AppearanceSettings.RESET;

public class Calculator {
    public static Runnable calculator() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println();
                String mathStatementString = scanner.nextLine().trim();

                if (mathStatementString.isEmpty()) {
                    System.out.print(UserInterface.centeringFunction(18) + AppearanceSettings.RED + "\nNo numbers entered." + RESET);
                    continue;
                }

                UserInterface.displayCalculatorOperationsList();
                System.out.print("Your choice is: ");
                String operation = scanner.nextLine().trim().toLowerCase();

                switch (operation) {
                    case "sum":
                    case "1":
                    case "+":
                        MathOperations.sum(mathStatementString);
                        break;
                    case "sub":
                    case "2":
                    case "-":
                        MathOperations.sub(mathStatementString);
                        break;
                    case "multi":
                    case "3":
                    case "*":
                        MathOperations.multi(mathStatementString);
                        break;
                    case "div":
                    case "4":
                    case "/":
                        MathOperations.divide(mathStatementString);
                        break;
                    case "pow":
                    case "5":
                    case "^":
                        MathOperations.pow(mathStatementString);
                        break;
                    case "exit":
                    case "6":
                    case "x":
                        System.out.println("Exiting the program...");
                        return null;
                    default:
                        System.out.println("Invalid operation.\n");

                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}