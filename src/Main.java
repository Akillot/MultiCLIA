import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" ");

            while(true) {
                try {
                    System.out.println("-------------------\nEnter your numbers.");
                    System.out.println("-------------------\nUse a 'SPACE'\nTo split numbers.\n-------------------");
                    String mathStatementString = scanner.nextLine();
                    Operations.operationMenu();
                    System.out.print("Your choice is: ");
                    String operation = scanner.nextLine();
                    System.out.println("-------------------");
                    switch (operation) {
                        case "sum":
                            Operations.sum(mathStatementString);
                            break;
                        case "subtract":
                            Operations.subtract(mathStatementString);
                            break;
                        case "multi":
                            Operations.multi(mathStatementString);
                            break;
                        case "divide":
                            Operations.divide(mathStatementString);
                            break;
                        case "power":
                            Operations.power(mathStatementString);
                            break;
                        case "exit":
                            return;
                        default:
                            System.out.println("Invalid operation.\n-------------------\n");
                    }
                } catch (Exception var6) {
                    Exception e = var6;
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
}
