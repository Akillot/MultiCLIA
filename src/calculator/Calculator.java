package calculator;

import layout.UserInterface;

import java.util.Scanner;

import static settings.AppearanceSettings.RESET;
import static settings.AppearanceSettings.YELLOW;

public class Calculator {
    public static Runnable calculator() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                UserInterface.transitionBorder();
                UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);

                UserInterface.delay = 200;
                String searchText = UserInterface.centeringFunction(10) + "Calculator\n";
                for (char ch : searchText.toCharArray()) {
                    System.out.print(ch);
                    try {
                        Thread.sleep(UserInterface.delay);
                    } catch (InterruptedException ex) {
                        UserInterface.displayRedCommands("Error, try again");
                    }
                }
                UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);
                System.out.println(UserInterface.centeringFunction(18) + "[" + YELLOW + "i" + RESET + "] " +
                        "Enter numbers\n" + UserInterface.centeringFunction(18) + "to calculate");
                UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);
                System.out.print(UserInterface.centeringFunction(18) + "Input: ");

                String mathStatementString = scanner.nextLine().trim();

                if (mathStatementString.isEmpty()) {
                    UserInterface.displayRedCommands("\nNo numbers entered");
                    continue;
                }

                UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);
                CalculatorFunctions.displayCalculatorOperationsList();
                System.out.print("Your choice is: ");
                String operation = scanner.nextLine().trim().toLowerCase();
                UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);
                UserInterface.transitionBorder();

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
                        UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);
                        UserInterface.displayRedCommands("Exiting the program...");
                        UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);
                        UserInterface.transitionBorder();
                        return null;
                    default:
                        UserInterface.displayRedCommands("Invalid operation");
                }
            } catch (Exception ex) {
                UserInterface.displayRedCommands("Error: " + ex.getMessage());
            }
        }
    }
}