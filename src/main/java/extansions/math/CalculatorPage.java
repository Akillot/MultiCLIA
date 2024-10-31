package extansions.math;

import java.util.Scanner;

import static extansions.math.CalculatorFunc.*;
import static core.layout.BorderFunc.*;
import static core.layout.ColorFunc.*;
import static core.layout.DisplayManager.*;
import static core.layout.TextFunc.alignment;

public class CalculatorPage {
    public static void calculator() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\n");
                message("--------------------", "white", 58);
                tip( "Enter numbers\n" + alignment(58) + "to calculate", 58);
                message("--------------------", "white", 58);
                System.out.print(alignment(58) + BOLD + WHITE + "Input: " + RESET);
                String mathStatementString = scanner.nextLine().trim();

                if (mathStatementString.isEmpty()) {
                    bigBorder();
                    message("No numbers entered", "red", 18);
                    continue;
                }
                System.out.println("\n");
                bigBorder();
                calculateOperation(mathStatementString);
                bigBorder();
                System.out.print(alignment(58) + BOLD + WHITE + "Your choice is: " + RESET);
                bigBorder();
            }
            catch (Exception ex) {
                errorAscii();
            }
        }
    }
}