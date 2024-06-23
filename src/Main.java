import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("\ndd-MM-yyyy \nHH:mm:ss");
        String formattedTime = localTime.format(myFormatter);
        System.out.println();

            while(true) {
                try {
                    System.out.println();
                    System.out.println(AppearanceFeatures.border + "\nEnter your numbers.");
                    System.out.println("Time is: " + formattedTime);
                    System.out.println(AppearanceFeatures.border + "\nUse a 'SPACE'\nTo split numbers.\n" + AppearanceFeatures.border);
                    String mathStatementString = scanner.nextLine().trim();

                    if (mathStatementString.isEmpty()) {
                        System.out.print(AppearanceFeatures.border + AppearanceFeatures.RED + "\n" + "No numbers entered." + AppearanceFeatures.RESET);
                        continue;
                    }

                    AdditionalOperations.commandList();
                    System.out.print("Your choice is: ");
                    String operation = scanner.nextLine().trim().toLowerCase();
                    System.out.println(AppearanceFeatures.border);

                    switch (operation) {
                        case "sum":
                        case "+":
                            MathOperations.sum(mathStatementString);
                            break;
                        case "sub":
                        case "-":
                            MathOperations.sub(mathStatementString);
                            break;
                        case "multi":
                        case "*":
                            MathOperations.multi(mathStatementString);
                            break;
                        case "div":
                        case "/":
                            MathOperations.divide(mathStatementString);
                            break;
                        case "pow":
                        case "^":
                            MathOperations.pow(mathStatementString);
                            break;
                        case "info":
                        case "i":
                            AdditionalOperations.versionInfo();
                            break;
                        case "exit":
                        case "x":
                            System.out.println("Exiting the program...");
                            return;
                        case "border-magic":
                            AppearanceFeatures.changeBorder();
                            break;
                        case "text-magic":
                            AppearanceFeatures.changeColor();
                            break;
                        case "back-magic":

                            break;
                        case "theme-magic":
                            AppearanceFeatures.changeTheme();
                            break;
                        default:
                            System.out.println(AppearanceFeatures.RED + "Invalid operation.\n" + AppearanceFeatures.RESET + AppearanceFeatures.border + "\n");
                    }
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
    }
}