package extensions.math;

import java.util.Scanner;

import static extensions.math.CalculatorFunc.*;
import static core.logic.BorderFunc.*;
import static core.logic.ColorFunc.*;
import static core.logic.DisplayManager.*;
import static core.logic.TextFunc.alignment;
import static java.lang.System.out;

public class CalculatorPage {
    public static void calculator() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                out.println("\n");
                message("--------------------", "white", 58,0);
                alert( "i","Enter numbers\n" + alignment(58) + "to calculate", 58);
                message("--------------------", "white", 58,0);
                out.print(alignment(58) + BOLD + WHITE + "Input: " + RESET);
                String mathStatementString = scanner.nextLine().trim();

                if (mathStatementString.isEmpty()) {
                    bigBorder();
                    message("No numbers entered", "red", 18,0);
                    continue;
                }
                out.println("\n");
                bigBorder();
                calculateOperation(mathStatementString);
                bigBorder();
                out.print(alignment(58) + BOLD + WHITE + "Your choice is: " + RESET);
                bigBorder();
            }
            catch (Exception ex) {
                errorAscii();
            }
        }
    }
}