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
                        UserInterface.displayError("Error, try again");
                    }
                }
                UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);
                System.out.println(UserInterface.centeringFunction(18) + "[" + YELLOW + "i" + RESET + "]" + "Enter numbers\n" + UserInterface.centeringFunction(18) + "to calculate");
                UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);
                System.out.print(UserInterface.centeringFunction(18) + "Input: ");

                String mathStatementString = scanner.nextLine().trim();

                if (mathStatementString.isEmpty()) {
                    UserInterface.displayError("\nNo numbers entered");
                    continue;
                }
                UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);
                UserInterface.displayCalculatorOperationsList();
                System.out.print("Your choice is: ");
                String operation = scanner.nextLine().trim().toLowerCase();
                UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);
                UserInterface.transitionBorder();

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
                    case "exit":
                    case "6":
                    case "x":
                        System.out.println("Exiting the program..."); //IN PROCESS
                        return null;
                    default:
                        UserInterface.displayError("Invalid operation");

                }
            } catch (Exception ex) {
                UserInterface.displayError("Error: " + ex.getMessage());
            }
        }
    }
}