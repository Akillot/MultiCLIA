package additional_packages.calculator;

import java.util.Scanner;

import static ui.layout.BorderWork.*;
import static ui.layout.ColorWork.*;
import static ui.layout.TextWork.contentAlignment;

public class Calculator {
    public static Runnable calculator() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                drawHorizontalBorder(numberOfSymbols);
                System.out.println(contentAlignment(18) + "[" + YELLOW + "i" + RESET + "] " +
                        "Enter numbers\n" + contentAlignment(18) + "to calculate");
                drawHorizontalBorder(numberOfSymbols);
                System.out.print(contentAlignment(18) + "Input: ");

                String mathStatementString = scanner.nextLine().trim();

                if (mathStatementString.isEmpty()) {
                    drawHorizontalBorder(numberOfSymbols);
                    displayColorCommand("No numbers entered", "red", (byte) 18);
                    continue;
                }

                drawHorizontalBorder(numberOfSymbols);
                CalculatorFunctions.displayCalculatorOperationsList();
                drawHorizontalBorder(numberOfSymbols);

                System.out.print(contentAlignment(18) + "Your choice is: ");
                String operation = scanner.nextLine().trim().toLowerCase();
                drawHorizontalBorder(numberOfSymbols);

                switch (operation) {
                    case "sum":
                    case "1":
                    case "+":
                        CalculatorFunctions.sum(mathStatementString);
                        break;
                    case "sub":
                    case "2":
                    case "-":
                        CalculatorFunctions.sub(mathStatementString);
                        break;
                    case "multi":
                    case "3":
                    case "*":
                        CalculatorFunctions.multi(mathStatementString);
                        break;
                    case "div":
                    case "4":
                    case "/":
                        CalculatorFunctions.divide(mathStatementString);
                        break;
                    case "pow":
                    case "5":
                    case "^":
                        CalculatorFunctions.pow(mathStatementString);
                        break;
                    case "exit":
                    case "6":
                    case "x":
                        drawTripleBorder();
                        displayColorCommand("Exiting the program...", "red", (byte) 18);
                        drawTripleBorder();
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