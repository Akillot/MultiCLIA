package additional_packages.math;

import java.util.Scanner;

import static additional_packages.math.CalculatorFunctions.displayCalculatorOperationsList;
import static ui.layout.BasicFunctions.displayTip;
import static ui.layout.BasicFunctions.exitApp;
import static ui.layout.BorderWork.*;
import static ui.layout.ColorWork.*;
import static ui.layout.TextWork.contentAlignment;
import static ui.layout.ThemesWork.displayErrorAscii;

public class CalculatorLayout {
    public static Runnable calculator() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\n");
                displayColorMessage("--------------------", "white", 58);
                displayTip( "Enter numbers\n" + contentAlignment(58) + "to calculate", 58);
                displayColorMessage("--------------------", "white", 58);
                System.out.print(contentAlignment(58) + BOLD + WHITE + "Input: " + RESET);
                String mathStatementString = scanner.nextLine().trim();

                if (mathStatementString.isEmpty()) {
                    drawTripleBorder();
                    displayColorMessage("No numbers entered", "red", 18);
                    continue;
                }
                System.out.println("\n");
                drawTripleBorder();
                displayCalculatorOperationsList();
                drawTripleBorder();

                displayColorMessage("Your choice is: ", "white", 58);
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
                        displayErrorAscii();
                        displayColorMessage("Invalid operation", "red", (byte) 0);
                }
            } catch (Exception ex) {
                displayColorMessage("Error: " + ex.getMessage(), "red", (byte) 0);
            }
        }
    }
}