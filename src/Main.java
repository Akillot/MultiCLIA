import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println();

            while(true) {
                try {
                    System.out.println(AdditionalOperations.border + "\nEnter your numbers.");
                    System.out.println(AdditionalOperations.border + "\nUse a 'SPACE'\nTo split numbers.\n" + AdditionalOperations.border);
                    String mathStatementString = scanner.nextLine().trim();

                    if (mathStatementString.isEmpty()) {
                        System.out.println("You have not entered any numbers.");
                        continue;
                    }

                    BasicOperations.operationMenu();
                    System.out.print("Your choice is: ");
                    String operation = scanner.nextLine().trim().toLowerCase();
                    System.out.println(AdditionalOperations.border);

                    switch (operation) {
                        case "sum":
                        case "+":
                            BasicOperations.sum(mathStatementString);
                            break;
                        case "sub":
                        case "-":
                            BasicOperations.sub(mathStatementString);
                            break;
                        case "multi":
                        case "*":
                            BasicOperations.multi(mathStatementString);
                            break;
                        case "div":
                        case "/":
                            BasicOperations.divide(mathStatementString);
                            break;
                        case "pow":
                        case "^":
                            BasicOperations.pow(mathStatementString);
                            break;
                        case "info":
                        case "i":
                        case "?":
                            BasicOperations.versionInfo();
                            break;
                        case "exit":
                        case "x":
                            System.out.println("Exiting the program...");
                            return;
                        case "abracadabra":
                            AdditionalOperations.changeBorder();
                            break;
                        default:
                            System.out.println("Invalid operation.\n" + AdditionalOperations.border + "\n");
                    }
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
    }
}
