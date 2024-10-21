package extansions.math;

import java.util.Scanner;

import static extansions.math.CalculatorFunc.*;
import static ui.layout.BorderFunc.*;
import static ui.layout.ColorFunc.*;
import static ui.layout.DisplayManager.*;
import static ui.layout.TextFunc.alignment;

public class CalculatorPage {
    public static void calculator() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\n");
                message("--------------------", "white", 58, false);
                tip( "Enter numbers\n" + alignment(58) + "to calculate", 58);
                message("--------------------", "white", 58, false);
                System.out.print(alignment(58) + BOLD + WHITE + "Input: " + RESET);
                String mathStatementString = scanner.nextLine().trim();

                if (mathStatementString.isEmpty()) {
                    displayBigBorder();
                    message("No numbers entered", "red", 18, false);
                    continue;
                }
                System.out.println("\n");
                displayBigBorder();
                calculateOperation(mathStatementString);
                displayBigBorder();

                System.out.print(alignment(58) + BOLD + WHITE + "Your choice is: " + RESET);

                displayBigBorder();
            }
            catch (Exception ex) {
                errorAscii();
            }
        }
    }
}