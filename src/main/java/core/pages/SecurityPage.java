package core.pages;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.*;
import java.util.Random;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;
import static core.logic.CommandManager.*;
import static core.ui.DisplayManager.*;
import static java.lang.System.out;

public class SecurityPage {

    private static final int easyComplexityColor = 85;

    private static final int mediumComplexityColor = 214;

    private static final int strongComplexityColor = 177;

    private static final int extraComplexityColor = 201;

    private static final String CHAR_POOL_EASY = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_POOL_MEDIUM = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String CHAR_POOL_STRONG = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";
    private static final String CHAR_POOL_EXTRA = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789<>.,/|\\?!+-*&^%$#@!~'}{)(";

    private static int passwordLength;

    public static void displaySecurityPage() {
        Security.addProvider(new BouncyCastleProvider());
        marginBorder(1, 2);
        message("Security:", sysLayoutColor, 58, 0, out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, searchingLineAlignment, false, getColor(sysLayoutColor) + "> ", "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "generate password", "/gp" -> passwordCreatorMenu();
                case "rerun", "/rr" -> {
                    modifyMessage('n',1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
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
        message("·  Generate password [" + getColor(sysMainColor) + "/gp"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  List Of Commands [" + getColor(sysMainColor) + "/lc"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Exit [" + getColor(sysMainColor) + "/e"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::println);
    }

    private static void passwordCreatorMenu() {
        modifyMessage('n', 1);
        out.print(alignment(58) + getColor(sysLayoutColor) + "Enter length of password [1-80]: ");
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
        out.print(alignment(58) + getColor(sysLayoutColor) + "Password complexity ["
                + getColor(easyComplexityColor) + "light" + getColor(sysLayoutColor) + "|"
                + getColor(easyComplexityColor) + "1" + getColor(sysLayoutColor) + ", "
                + getColor(mediumComplexityColor) + "medium" + getColor(sysLayoutColor) + "|"
                + getColor(mediumComplexityColor) + "2" + getColor(sysLayoutColor) + ", "
                + getColor(strongComplexityColor) + "strong" + getColor(sysLayoutColor) + "|"
                + getColor(strongComplexityColor) + "3" + getColor(sysLayoutColor) + ", "
                + getColor(extraComplexityColor) + "extra" + getColor(sysLayoutColor) + "|"
                + getColor(extraComplexityColor) + "4" + getColor(sysLayoutColor) + "]: ");

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
            case "light", "1" -> {
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

        return getColor(color) + generatePasswordFromPool(charPool);
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