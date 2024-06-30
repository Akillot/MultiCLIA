import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("\ndd-MM-yyyy \nHH:mm");
        String formattedTime = localTime.format(myFormatter);
        System.out.println();

        System.out.println("\n" + AppearanceFeatures.border);
        System.out.print("    +---------+" + "\n    |" + AppearanceFeatures.RED + "M" + AppearanceFeatures.RESET +
                AppearanceFeatures.GREEN + "u" + AppearanceFeatures.RESET +
                AppearanceFeatures.YELLOW + "l" + AppearanceFeatures.RESET +
                AppearanceFeatures.BLUE + "t" + AppearanceFeatures.RESET +
                AppearanceFeatures.PURPLE + "i" + AppearanceFeatures.RESET + "CLIA|\n" + "    +---------+");

            while(true) {
                try {
                    System.out.println();
                    System.out.println(AppearanceFeatures.border + "\nTime is: " + formattedTime);
                    System.out.println(AppearanceFeatures.border + "\nEnter your numbers.");
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
                        case "magic":
                        case "_":
                            AppearanceFeatures.basicChanges();
                            break;
                        case "info":
                        case "7":
                        case "i":
                            AdditionalOperations.versionInfo();
                            break;
                        case "exit":
                        case "8":
                        case "x":
                            System.out.println("Exiting the program...");
                            return;
                        default:
                            System.out.println(AppearanceFeatures.RED + "Invalid operation.\n" +
                                    AppearanceFeatures.RESET + AppearanceFeatures.border + "\n");
                    }
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
    }
}