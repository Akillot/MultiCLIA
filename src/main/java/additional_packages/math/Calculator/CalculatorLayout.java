package additional_packages.math.Calculator;

import java.util.Scanner;

import static additional_packages.math.Calculator.CalculatorFunctions.*;
import static ui.layout.BasicFunc.displayTip;
import static ui.layout.BorderFunc.*;
import static ui.layout.ColorFunc.*;
import static ui.layout.TextFunc.contentAlignment;
import static ui.layout.ThemesFunc.displayErrorAscii;

public class CalculatorLayout {
    public static void calculator() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\n");
                displayContent("--------------------", "white", 58);
                displayTip( "Enter numbers\n" + contentAlignment(58) + "to calculate", 58);
                displayContent("--------------------", "white", 58);
                System.out.print(contentAlignment(58) + BOLD + WHITE + "Input: " + RESET);
                String mathStatementString = scanner.nextLine().trim();

                if (mathStatementString.isEmpty()) {
                    drawTripleBorder();
                    displayContent("No numbers entered", "red", 18);
                    continue;
                }
                System.out.println("\n");
                drawTripleBorder();
                calculateOperation(mathStatementString);
                drawTripleBorder();

                System.out.print(contentAlignment(58) + BOLD + WHITE + "Your choice is: " + RESET);

                drawTripleBorder();
            }
            catch (Exception ex) {
                displayErrorAscii();
                displayContent("Error: " + ex.getMessage(), "red", (byte) 0);
            }
        }
    }
}