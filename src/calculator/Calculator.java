package calculator;

import java.util.Scanner;

import static calculator.CalculatorFunctions.*;
import static layout.UserInterface.*;
import static settings.AppearanceSettings.RESET;
import static settings.AppearanceSettings.YELLOW;

public class Calculator {
    public static Runnable calculator() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                transitionBorder();
                drawHorizontalBorder(numberOfSymbols);

                delay = 200;
                String searchText = contentAlignment(10) + "Calculator\n";
                for (char ch : searchText.toCharArray()) {
                    System.out.print(ch);
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException ex) {
                        displayColorCommand("Error, try again", "red", (byte) 0);
                    }
                }
                drawHorizontalBorder(numberOfSymbols);
                System.out.println(contentAlignment(18) + "[" + YELLOW + "i" + RESET + "] " +
                        "Enter numbers\n" + contentAlignment(18) + "to calculate");
                drawHorizontalBorder(numberOfSymbols);
                System.out.print(contentAlignment(18) + "Input: ");

                String mathStatementString = scanner.nextLine().trim();

                if (mathStatementString.isEmpty()) {
                    displayColorCommand("\nNo numbers entered", "red", (byte) 0);
                    continue;
                }

                drawHorizontalBorder(numberOfSymbols);
                displayCalculatorOperationsList();
                System.out.print(contentAlignment(18) + "Your choice is: ");
                String operation = scanner.nextLine().trim().toLowerCase();
                drawHorizontalBorder(numberOfSymbols);
                transitionBorder();

                switch (operation) {
                    case "sum":
                    case "1":
                    case "+":
                        sum(mathStatementString);
                        break;
                    case "sub":
                    case "2":
                    case "-":
                        sub(mathStatementString);
                        break;
                    case "multi":
                    case "3":
                    case "*":
                        multi(mathStatementString);
                        break;
                    case "div":
                    case "4":
                    case "/":
                        divide(mathStatementString);
                        break;
                    case "pow":
                    case "5":
                    case "^":
                        pow(mathStatementString);
                        break;
                    case "exit":
                    case "6":
                    case "x":
                        drawHorizontalBorder(numberOfSymbols);
                        displayColorCommand("Exiting the program...", "red", (byte) 18);
                        drawHorizontalBorder(numberOfSymbols);
                        transitionBorder();
                        return null;
                    default:
                        displayColorCommand("Invalid operation", "red", (byte) 0);
                }
            } catch (Exception ex) {
                displayColorCommand("Error: " + ex.getMessage(), "red", (byte) 0);
            }
        }
    }
}