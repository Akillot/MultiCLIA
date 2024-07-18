package calculator;

import layout.UserInterface;
import settings.AppearanceSettings;

import java.util.Scanner;

import static settings.AppearanceSettings.*;

public class Calculator {
    public static Runnable calculator() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("|" + UserInterface.centeringFunction(-19) + "|");
                UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);

                UserInterface.delay = 200;
                String searchText = UserInterface.centeringFunction(10) + "Calculator\n";
                for (char ch : searchText.toCharArray()) {
                    System.out.print(ch);
                    try {
                        Thread.sleep(UserInterface.delay);
                    } catch (InterruptedException ex) {
                        System.out.println(RED + "Error, try again" + RESET);
                    }
                }
                UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);
                System.out.println(UserInterface.centeringFunction(18) + "[" + YELLOW + "i" + RESET + "]" + "Enter numbers\n" + UserInterface.centeringFunction(18) + "to calculate");
                UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);

                String mathStatementString = scanner.nextLine().trim();

                if (mathStatementString.isEmpty()) {
                    System.out.print(UserInterface.centeringFunction(18) + AppearanceSettings.RED + "\nNo numbers entered." + RESET);
                    continue;
                }
                UserInterface.drawHorizontalBorder(UserInterface.numberOfSymbols);
                UserInterface.displayCalculatorOperationsList();
                System.out.print("Your choice is: ");
                String operation = scanner.nextLine().trim().toLowerCase();

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
                        System.out.println("Exiting the program...");
                        return null;
                    default:
                        System.out.println("Invalid operation.\n");

                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}