package core.pages;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

import static core.configs.AppearanceConfigs.*;
import static core.configs.AppearanceConfigs.systemLayoutColor;
import static core.configs.TextConfigs.*;
import static core.configs.TextConfigs.message;
import static core.logic.CommandManager.exitPage;
import static core.ui.DisplayManager.scanner;
import static java.lang.System.out;

public class SecurityPage {
    public static void displaySecurityPage() {
        marginBorder(1, 2);
        message("Security: ", systemLayoutColor, 58, 0, out::print);
        displayListOfCommands();

        while (true) {
            modifyMessage('n', 1);
            slowMotionText(0, 56, false,
                    getAnsi256Color(systemLayoutColor) + "> ", "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "generate password", "/gp" -> passwordCreatorMenu();
                case "list of commands", "/lc" -> displayListOfCommands();
                case "exit", "/e" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands() {
        modifyMessage('n', 1);
        message("·  Generate password [" + getAnsi256Color(systemMainColor)
                + "/gp" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);

        message("·  List Of Commands [" + getAnsi256Color(systemMainColor)
                + "/lc" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);

        message("·  Exit [" + getAnsi256Color(systemRejectionColor)
                + "/e" + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);
    }

    @Setter
    @Getter
    private static String password;

    @Setter
    @Getter
    private static int passwordLength;

    private static void passwordCreatorMenu() {
        Scanner scanner = new Scanner(System.in);

        modifyMessage('n', 1);
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Enter length of password: ");
        passwordLength = scanner.nextInt();
        modifyMessage('n', 1);

        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Password complexity ["
                + getAnsi256Color(85) + "easy" + getAnsi256Color(systemLayoutColor) + "|"
                + getAnsi256Color(85) + "1" + getAnsi256Color(systemLayoutColor) + ", "

                + getAnsi256Color(214) + "medium" + getAnsi256Color(systemLayoutColor)
                + getAnsi256Color(systemLayoutColor) + "|" + getAnsi256Color(214) + "2"
                + getAnsi256Color(systemLayoutColor) + ", "

                + getAnsi256Color(177) + "strong" + getAnsi256Color(systemLayoutColor)
                + getAnsi256Color(systemLayoutColor) + "|" + getAnsi256Color(177) + "3"
                + getAnsi256Color(systemLayoutColor) + ", "

                + getAnsi256Color(201) + "extra" + getAnsi256Color(systemLayoutColor)
                + getAnsi256Color(systemLayoutColor) + "|" + getAnsi256Color(201) + "4"
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
            case "easy", "1" -> charPool = "abcdefghijklmnopqrstuvwxyz";
            case "normal", "2" -> charPool = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            case "strong", "3" -> charPool = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";
            case "extra", "4" -> charPool = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789<>.,/|\\?!+-*&^%$#@!~'`}{)(";
            default -> {
                return null;
            }
        }

        StringBuilder passwordBuilder = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(charPool.length());
            passwordBuilder.append(charPool.charAt(index));
        }

        return passwordBuilder.toString();
    }
}