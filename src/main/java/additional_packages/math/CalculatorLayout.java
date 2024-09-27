package additional_packages.math;

import java.util.Scanner;

import static ui.layout.BasicFunctions.exitApp;
import static ui.layout.BorderWork.*;
import static ui.layout.ColorWork.*;
import static ui.layout.TextWork.contentAlignment;

public class CalculatorLayout {
    public static Runnable calculator() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\n");
                System.out.println(contentAlignment(18) + "[" + WHITE + "i" + RESET + "] " +
                        "Enter numbers\n" + contentAlignment(18) + "to calculate");
                drawTripleBorder();
                System.out.print(contentAlignment(18) + "Input: ");

                String mathStatementString = scanner.nextLine().trim();

                if (mathStatementString.isEmpty()) {
                    drawTripleBorder();
                    displayColorMessage("No numbers entered", "red", (byte) 18);
                    continue;
                }

                drawTripleBorder();
                CalculatorFunctions.displayCalculatorOperationsList();
                drawTripleBorder();

                System.out.print(contentAlignment(18) + "Your choice is: ");
                String operation = scanner.nextLine().trim().toLowerCase();
                drawTripleBorder();

                switch (operation) {
                    case "sum":
                    case "1":
                    case "+":
                        CalculatorFunctions.summationOperation(mathStatementString);
                        break;
                    case "sub":
                    case "2":
                    case "-":
                        CalculatorFunctions.subtractionOperation(mathStatementString);
                        break;
                    case "multi":
                    case "3":
                    case "*":
                        CalculatorFunctions.multiplyingOperation(mathStatementString);
                        break;
                    case "div":
                    case "4":
                    case "/":
                        CalculatorFunctions.dividingOperation(mathStatementString);
                        break;
                    case "pow":
                    case "5":
                    case "^":
                        CalculatorFunctions.powerOperation(mathStatementString);
                        break;
                    case "exit":
                    case "6":
                    case "x":
                        drawTripleBorder();
                        exitApp();
                        drawTripleBorder();
                        return null;
                    default:
                        displayColorMessage("Invalid operation", "red", (byte) 0);
                }
            } catch (Exception ex) {
                displayColorMessage("Error: " + ex.getMessage(), "red", (byte) 0);
            }
        }
    }
}