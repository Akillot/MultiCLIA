package calculator;

import additional.AdditionalOperations;
import layout.layoutSettings;
import settings.AppearanceSettings;
import settings.BasicSettings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static settings.AppearanceSettings.RESET;
import static settings.AppearanceSettings.border;

public class Calculator {
    public static void calculator() {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("\ndd-MM-yyyy \nHH:mm");
        String formattedTime = localTime.format(myFormatter);
        System.out.println();

        System.out.println("\n" + AppearanceSettings.border);
        layoutSettings.showLogo();

        while (true) {
            try {
                System.out.println();
                System.out.println(border + "\nTime is: " + formattedTime);
                System.out.println(border + "\nEnter your numbers.");
                System.out.println(border + "\nUse a 'SPACE'\nTo split numbers.\n" + border);
                String mathStatementString = scanner.nextLine().trim();

                if (mathStatementString.isEmpty()) {
                    System.out.print(border + "\n" + "No numbers entered." + RESET);
                    continue;
                }

                AdditionalOperations.showCommandList();
                System.out.print("Your choice is: ");
                String operation = scanner.nextLine().trim().toLowerCase();
                System.out.println(border);

                switch (operation) {
                    case "sum":
                    case "1":
                    case "+":
                        MathOperations.sum(mathStatementString);
                        break;
                    case "sub":
                    case "2":
                    case "-":
                        MathOperations.sub(mathStatementString);
                        break;
                    case "multi":
                    case "3":
                    case "*":
                        MathOperations.multi(mathStatementString);
                        break;
                    case "div":
                    case "4":
                    case "/":
                        MathOperations.divide(mathStatementString);
                        break;
                    case "pow":
                    case "5":
                    case "^":
                        MathOperations.pow(mathStatementString);
                        break;
                    case "6":
                    case "settings":
                        BasicSettings.versionInfo();
                        break;
                    case "exit":
                    case "7":
                    case "x":
                        System.out.println("Exiting the program...");
                        return;
                    default:
                        System.out.println("Invalid operation.\n" + border + "\n");

                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}
