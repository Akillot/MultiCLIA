package calculator;

import additional.AdditionalOperations;
import layout.LayoutSettings;
import settings.BasicSettings;

import java.util.Scanner;

import static settings.AppearanceSettings.RESET;

public class Calculator {
    public static void calculator() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();

        System.out.println("\n" + LayoutSettings.fullBorder);
        LayoutSettings.showLogo();

        while (true) {
            try {
                System.out.println();
                String mathStatementString = scanner.nextLine().trim();

                if (mathStatementString.isEmpty()) {
                    System.out.print(LayoutSettings.fullBorder + "\n" + "No numbers entered." + RESET);
                    continue;
                }

                AdditionalOperations.showCommandList();
                System.out.print("Your choice is: ");
                String operation = scanner.nextLine().trim().toLowerCase();
                System.out.println(LayoutSettings.fullBorder);

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
                    case "6":
                    case "settings":
                        BasicSettings.showVersionInfo();
                        break;
                    case "exit":
                    case "7":
                    case "x":
                        System.out.println("Exiting the program...");
                        return;
                    default:
                        System.out.println("Invalid operation.\n" + LayoutSettings.fullBorder + "\n");

                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}