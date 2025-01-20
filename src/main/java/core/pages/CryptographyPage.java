package core.pages;

import org.jetbrains.annotations.NotNull;
import java.util.Scanner;

import static core.configs.AppearanceConfigs.*;
import static core.logic.CommandManager.exitPage;
import static core.ui.DisplayManager.scanner;
import static core.configs.TextConfigs.*;
import static java.lang.System.out;

public class CryptographyPage {

    public static void displayEncryptionPage() {
        marginBorder(1, 2);
        message("Encryption Menu:", systemLayoutColor, 58, 0, out::print);
        displayListOfCommands();

        while (true) {
            modifyMessage('n', 1);
            slowMotionText(0, searchingLineAlignment, false,
                    getAnsi256Color(systemLayoutColor) + "> ", "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "make encrypt", "/me" -> encryptionMenu();
                case "make decrypt", "/md" -> decryptionMenu();
                case "list of commands", "/lc" -> displayListOfCommands();
                case "exit", "/e" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands(){
        modifyMessage('n',1);
        message("·  Make Encryption [" + getAnsi256Color(systemMainColor)
                + "/me" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 58, 0, out::print);

        message("·  Make Decryption ["  + getAnsi256Color(systemMainColor)
                + "/md" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 58, 0, out::print);

        message("·  List Of Commands [" + getAnsi256Color(systemMainColor)
                + "/lc" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 58, 0, out::print);

        message("·  Exit [" + getAnsi256Color(systemMainColor)
                + "/e" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 58, 0, out::print);
    }

    private static void encryptionMenu() {
        Scanner scanner = new Scanner(System.in);

        modifyMessage('n', 1);
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Enter text to encrypt: ");
        String textToEncrypt = scanner.nextLine();

        String encryptedText = encryptText(textToEncrypt);
        message("Encrypted Text: " + encryptedText, systemMainColor, 58, 0, out::print);
    }

    private static @NotNull String encryptText(String text) {
        return new StringBuilder(text).reverse().toString();
    }

    private static void decryptionMenu() {
        Scanner scanner = new Scanner(System.in);

        modifyMessage('n', 1);
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Enter text to decrypt: ");
        String textToDecrypt = scanner.nextLine();

        String decryptedText = decryptText(textToDecrypt);
        message("Decrypted Text: " + decryptedText, systemMainColor, 58, 0, out::print);
    }

    private static @NotNull String decryptText(String text) {
        return new StringBuilder(text).reverse().toString();
    }
}