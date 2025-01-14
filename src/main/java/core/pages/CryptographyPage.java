package core.pages;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

import static core.logic.AppearanceConfigs.*;
import static core.logic.CommandManager.exitPage;
import static core.logic.DisplayManager.scanner;
import static core.logic.TextConfigs.*;
import static java.lang.System.out;

public class CryptographyPage {

    @Setter
    @Getter
    private static String password;

    private static void passwordCreatorMenu() {
        Scanner scanner = new Scanner(System.in);

        modifyMessage('n',1);
        message("Enter values", systemLayoutColor, 58, 0, out::print);
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Password complexity ["
                + getAnsi256Color(85) + "easy" + getAnsi256Color(systemLayoutColor) + "|"
                + getAnsi256Color(85) + "1" + getAnsi256Color(systemLayoutColor) + ", "

                + getAnsi256Color(214) + "medium" + getAnsi256Color(systemLayoutColor)
                + getAnsi256Color(systemLayoutColor) + "|" + getAnsi256Color(214) + "2"
                + getAnsi256Color(systemLayoutColor) + ", "

                + getAnsi256Color(177) + "strong" + getAnsi256Color(systemLayoutColor)
                + getAnsi256Color(systemLayoutColor) + "|" + getAnsi256Color(177) + "3"
                + getAnsi256Color(systemLayoutColor) + "]: ");
        String passwordComplexity = scanner.nextLine().toLowerCase();

        String generatedPassword = createPassword(passwordComplexity);
        if (generatedPassword != null) {
            message("Generated Password: " + getAnsi256Color(systemMainColor) + generatedPassword,
                    systemLayoutColor, 58, 0, out::print);
        } else {
            out.print("");
        }
    }

    private static @Nullable String createPassword(@NotNull String passwordComplexity) {
        int length;
        String charPool;

        switch (passwordComplexity) {
            case "easy","1" -> {
                length = 8;
                charPool = "abcdefghijklmnopqrstuvwxyz";
            }
            case "medium","2" -> {
                length = 12;
                charPool = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            }
            case "strong","3" -> {
                length = 16;
                charPool = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";
            }
            default -> {
                return null;
            }
        }

        StringBuilder passwordBuilder = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charPool.length());
            passwordBuilder.append(charPool.charAt(index));
        }

        return passwordBuilder.toString();
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
        return new StringBuilder(text).reverse().toString(); // Простая расшифровка
    }

    private static void displayListOfCommands(){
        modifyMessage('n',1);
        message("·  Password Generator [" + getAnsi256Color(systemMainColor)
                + "/p" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);

        message("·  Encryption [" + getAnsi256Color(systemMainColor)
                + "/ec" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);

        message("·  Decryption ["  + getAnsi256Color(systemMainColor)
                + "/dc" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);

        message("·  List Of Commands [" + getAnsi256Color(systemMainColor)
                + "/lc" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);

        message("·  Exit [" + getAnsi256Color(systemRejectionColor)
                + "/e" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);
    }

    public static void displayEncryptionPage() {
        marginBorder(1, 2);
        message("Encryption Menu:", systemLayoutColor, 58, 0, out::print);
        displayListOfCommands();

        while (true) {
            modifyMessage('n', 1);
            slowMotionText(0, 56, false,
                    getAnsi256Color(systemLayoutColor) + "> ", "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "password generator", "/p" -> passwordCreatorMenu();
                case "encrypt", "/ec" -> encryptionMenu();
                case "decrypt", "/dc" -> decryptionMenu();
                case "list of commands", "/lc" -> displayListOfCommands();
                case "exit", "/e" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }
}
