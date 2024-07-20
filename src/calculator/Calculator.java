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
                String searchText = centeringFunction(10) + "Calculator\n";
                for (char ch : searchText.toCharArray()) {
                    System.out.print(ch);
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException ex) {
                        displayRedCommands("Error, try again");
                    }
                }
                drawHorizontalBorder(numberOfSymbols);
                System.out.println(centeringFunction(18) + "[" + YELLOW + "i" + RESET + "] " +
                        "Enter numbers\n" + centeringFunction(18) + "to calculate");
                drawHorizontalBorder(numberOfSymbols);
                System.out.print(centeringFunction(18) + "Input: ");

                String mathStatementString = scanner.nextLine().trim();

                if (mathStatementString.isEmpty()) {
                    displayRedCommands("\nNo numbers entered");
                    continue;
                }

                drawHorizontalBorder(numberOfSymbols);
                displayCalculatorOperationsList();
                System.out.print(centeringFunction(18) + "Your choice is: ");
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
                        displayRedCommands("Exiting the program...");
                        drawHorizontalBorder(numberOfSymbols);
                        transitionBorder();
                        return null;
                    default:
                        displayRedCommands("Invalid operation");
                }
            } catch (Exception ex) {
                displayRedCommands("Error: " + ex.getMessage());
            }
        }
    }
}