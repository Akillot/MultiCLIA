package core.pages;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

import static core.logic.AppearanceConfigs.*;
import static core.logic.AppearanceConfigs.systemLayoutColor;
import static core.logic.CommandManager.exitPage;
import static core.logic.DisplayManager.scanner;
import static core.logic.TextConfigs.*;
import static java.lang.System.out;

public class EncryptionPage {

    @Setter
    @Getter
    private static String password;

    private static void createPassword(){
        Scanner scanner = new Scanner(System.in);

        message("Enter values", systemLayoutColor, 58, 0, out::print);
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Password complexity ["
                + getAnsi256Color(85) + "easy" + getAnsi256Color(systemLayoutColor) + ", "
                + getAnsi256Color(214) + "medium" + getAnsi256Color(systemLayoutColor)
                + ", " + getAnsi256Color(systemRejectionColor) + "strong" + getAnsi256Color(systemLayoutColor) + "]: ");
        String passwordComplexity = scanner.nextLine().toLowerCase();
    }

    public static void displayEncryptionPage() {
        marginBorder(1,2);
        message("Encryption Menu:", systemLayoutColor, 58, 0, out::print);
        //displayListOfCommands();

        while (true) {
            modifyMessage('n',1);
            slowMotionText(0, 56, false,
                    getAnsi256Color(systemLayoutColor) + "> ", "");
            String input = scanner.nextLine().toLowerCase();

            /*switch (input) {
                case "memory", "/m" -> ;
                case "cpu", "/c" -> ;
                case "colors", "/cl" -> ;
                case "logos", "/l" -> ;
                case "list of commands", "/lc" -> ;
                case "exit", "/e" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
             */
        }
    }
}