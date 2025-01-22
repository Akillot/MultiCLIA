package core.pages;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.SecureRandom;
import java.util.Random;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;
import static core.logic.CommandManager.exitPage;
import static core.pages.StartPage.mainMenuRerun;
import static core.ui.DisplayManager.clearTerminal;
import static core.ui.DisplayManager.scanner;
import static java.lang.System.out;

public class SecurityPage {

    @Getter
    private static final int easyComplexityColor = 85;

    @Getter
    private static final int mediumComplexityColor = 214;

    @Getter
    private static final int strongComplexityColor = 177;

    @Getter
    private static final int extraComplexityColor = 201;

    private static final String CHAR_POOL_EASY = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_POOL_MEDIUM = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String CHAR_POOL_STRONG = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";
    private static final String CHAR_POOL_EXTRA = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789<>.,/|\\?!+-*&^%$#@!~'}{)(";

    @Setter @Getter
    private static int passwordLength;

    public static void displaySecurityPage() {
        marginBorder(1, 2);
        message("Security:", sysLayoutColor, 58, 0, out::println);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, searchingLineAlignment, false, getAnsi256Color(sysLayoutColor) + "> ",
                    "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "generate password", "/gp" -> passwordCreatorMenu();
                case "rerun", "/rr" -> mainMenuRerun();
                case "clear terminal", "/cl" -> clearTerminal();
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
        message("·  Generate password [" + getAnsi256Color(sysMainColor) + "/gp"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);
        message("·  List Of Commands [" + getAnsi256Color(sysMainColor) + "/lc"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);
        message("·  Exit [" + getAnsi256Color(sysMainColor) + "/e"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::println);
    }

    private static void passwordCreatorMenu() {
        modifyMessage('n', 1);
        out.print(alignment(58) + getAnsi256Color(sysLayoutColor) + "Enter length of password (1-80): ");
        try {
            passwordLength = scanner.nextInt();
            if (passwordLength <= 0 || passwordLength > 80) {
                message("Invalid password length. Please enter a number between 1 and 80.",
                        sysLayoutColor, 58, 0, out::println);
                return;
            }
        } catch (Exception e) {
            message("Invalid input. Please enter a number.", sysLayoutColor, 58, 0, out::print);
            scanner.nextLine();
            return;
        }

        scanner.nextLine();

        modifyMessage('n', 1);
        out.print(alignment(58) + getAnsi256Color(sysLayoutColor) + "Password complexity ["
                + getAnsi256Color(easyComplexityColor) + "easy" + getAnsi256Color(sysLayoutColor) + "|"
                + getAnsi256Color(easyComplexityColor) + "1" + getAnsi256Color(sysLayoutColor) + ", "
                + getAnsi256Color(mediumComplexityColor) + "medium" + getAnsi256Color(sysLayoutColor) + "|"
                + getAnsi256Color(mediumComplexityColor) + "2" + getAnsi256Color(sysLayoutColor) + ", "
                + getAnsi256Color(strongComplexityColor) + "strong" + getAnsi256Color(sysLayoutColor) + "|"
                + getAnsi256Color(strongComplexityColor) + "3" + getAnsi256Color(sysLayoutColor) + ", "
                + getAnsi256Color(extraComplexityColor) + "extra" + getAnsi256Color(sysLayoutColor) + "|"
                + getAnsi256Color(extraComplexityColor) + "4" + getAnsi256Color(sysLayoutColor) + "]: ");

        String passwordComplexity = scanner.nextLine().toLowerCase();
        String generatedPassword = createPassword(passwordComplexity);
        if (generatedPassword != null) {
            modifyMessage('n', 1);
            message("Generated Password: " + generatedPassword,
                    sysLayoutColor, 58, 0, out::println);
        } else {
            message("Invalid complexity option. Please try again.", sysLayoutColor, 58, 0, out::println);
        }
    }

    private static @Nullable String createPassword(@NotNull String passwordComplexity) {
        String charPool;
        int color;

        switch (passwordComplexity) {
            case "easy", "1" -> {
                charPool = CHAR_POOL_EASY;
                color = easyComplexityColor;
            }
            case "medium", "2" -> {
                charPool = CHAR_POOL_MEDIUM;
                color = mediumComplexityColor;
            }
            case "strong", "3" -> {
                charPool = CHAR_POOL_STRONG;
                color = strongComplexityColor;
            }
            case "extra", "4" -> {
                charPool = CHAR_POOL_EXTRA;
                color = extraComplexityColor;
            }
            default -> {
                return null;
            }
        }

        return getAnsi256Color(color) + generatePasswordFromPool(charPool);
    }

    private static @NotNull String generatePasswordFromPool(@NotNull String charPool) {
        StringBuilder passwordBuilder = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(charPool.length());
            passwordBuilder.append(charPool.charAt(index));
        }

        return passwordBuilder.toString();
    }
}