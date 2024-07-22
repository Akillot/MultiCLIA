package calculator;

import java.util.Scanner;

import static calculator.CalculatorFunctions.*;
import static layout.Stylization.*;

public class Calculator {
    public static Runnable calculator() {
        Scanner scanner = new Scanner(System.in);
        displaySlowMotionText(250, 10, false, "Calculator", "\n");
        while (true) {
            try {
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
                drawHorizontalBorder(numberOfSymbols);

                System.out.print(contentAlignment(18) + "Your choice is: ");
                String operation = scanner.nextLine().trim().toLowerCase();
                drawHorizontalBorder(numberOfSymbols);

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
                        drawFullTripleBorder();
                        displayColorCommand("Exiting the program...", "red", (byte) 18);
                        drawFullTripleBorder();
                        return null;
                    default:
                        drawFullTripleBorder();
                        displayColorCommand("Invalid operation", "red", (byte) 0);
                }
            } catch (Exception ex) {
                displayColorCommand("Error: " + ex.getMessage(), "red", (byte) 0);
            }
        }
    }
}