package core.pages;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;
import static core.logic.CommandManager.exitPage;
import static core.ui.DisplayManager.scanner;
import static java.lang.System.out;

public class SecurityPage {

    @Getter
    private static int easy_complexity_color = 85;

    @Getter
    private static int medium_complexity_color = 214;

    @Getter
    private static int strong_complexity_color = 177;

    @Getter
    private static int extra_complexity_color = 201;

    @Getter
    private static String charPool;

    //Fix the bug with incorrect values
    private static final String CHAR_POOL_EASY = getAnsi256Color(easy_complexity_color) + "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_POOL_MEDIUM = getAnsi256Color(medium_complexity_color) + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String CHAR_POOL_STRONG = getAnsi256Color(strong_complexity_color) + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";
    private static final String CHAR_POOL_EXTRA = getAnsi256Color(extra_complexity_color) + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789<>.,/|\\?!+-*&^%$#@!~'}{)(";

    @Setter @Getter private static String password;
    @Setter @Getter private static int passwordLength;

    public static void displaySecurityPage() {
        marginBorder(1, 2);
        message("Security: ", systemLayoutColor, 58, 0, out::print);
        displayListOfCommands();

        while (true) {
            modifyMessage('n', 1);
            slowMotionText(0, 56, false, getAnsi256Color(systemLayoutColor) + "> ", "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "generate password", "/gp", "/p" -> passwordCreatorMenu();
                case "list of commands", "/lc" -> displayListOfCommands();
                case "exit", "/e" -> {
                    exitPage();
                    return;
                }
                default -> modifyMessage('n', 1);
            }
        }
    }

    private static void displayListOfCommands() {
        modifyMessage('n', 1);
        message("·  Generate password [" + getAnsi256Color(systemMainColor) + "/gp"
                + getAnsi256Color(systemLayoutColor) +" or " + getAnsi256Color(systemMainColor) + "/p"
                + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);
        message("·  List Of Commands [" + getAnsi256Color(systemMainColor) + "/lc"
                + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);
        message("·  Exit [" + getAnsi256Color(systemRejectionColor) + "/e"
                + getAnsi256Color(systemLayoutColor) + "]", systemLayoutColor, 48, 0, out::print);
    }

    private static void passwordCreatorMenu() {
        Scanner scanner = new Scanner(System.in);

        modifyMessage('n', 1);
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Enter length of password: ");
        passwordLength = scanner.nextInt();

        //Add checking for an empty value

        if (passwordLength <= 0 || password.length() < passwordLength || password.length() > 80) {
            return;
        }
        scanner.nextLine();

        modifyMessage('n', 1);
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Password complexity ["
                + getAnsi256Color(easy_complexity_color) + "easy" + getAnsi256Color(systemLayoutColor) + "|"
                + getAnsi256Color(easy_complexity_color) + "1" + getAnsi256Color(systemLayoutColor) + ", "
                + getAnsi256Color(medium_complexity_color) + "medium" + getAnsi256Color(systemLayoutColor) + "|"
                + getAnsi256Color(medium_complexity_color) + "2" + getAnsi256Color(systemLayoutColor) + ", "
                + getAnsi256Color(strong_complexity_color) + "strong" + getAnsi256Color(systemLayoutColor) + "|"
                + getAnsi256Color(strong_complexity_color) + "3" + getAnsi256Color(systemLayoutColor) + ", "
                + getAnsi256Color(extra_complexity_color) + "extra" + getAnsi256Color(systemLayoutColor) + "|"
                + getAnsi256Color(extra_complexity_color) + "4" + getAnsi256Color(systemLayoutColor) + "]: ");

        String passwordComplexity = scanner.nextLine().toLowerCase();
        String generatedPassword = createPassword(passwordComplexity);
        if (generatedPassword != null) {
            out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Generated Password: "
                    + generatedPassword);
            modifyMessage('n', 1);
        } else {
            modifyMessage('n', 1);
        }
    }

    private static @Nullable String createPassword(@NotNull String passwordComplexity) {

        switch (passwordComplexity) {
            case "easy", "1" -> charPool = CHAR_POOL_EASY;
            case "medium", "2" -> charPool = CHAR_POOL_MEDIUM;
            case "strong", "3" -> charPool = CHAR_POOL_STRONG;
            case "extra", "4" -> charPool = CHAR_POOL_EXTRA;
            default -> {
                return null;
            }
        }

        return generatePasswordFromPool(charPool);
    }

    private static @NotNull String generatePasswordFromPool(@NotNull String charPool) {
        StringBuilder passwordBuilder = new StringBuilder();
        Random random = new SecureRandom();

        String passwordColor = charPool.substring(0, charPool.indexOf('a'));

        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(charPool.length());
            passwordBuilder.append(charPool.charAt(index));
        }

        return passwordColor + passwordBuilder;
    }
}