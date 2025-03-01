package core.ui.extensions.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.*;
import java.util.Random;

import static core.logic.CommandManager.*;
import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.ui.essential.configs.DisplayManager.scanner;
import static core.ui.essential.configs.TextConfigs.*;
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
        message("Security:", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false, getColor(sysLayoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "generate password", "/gp" -> passwordCreatorMenu();
                case "restart", "/rs" -> {
                    insertControlChars('n',1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
                case "list", "/ls" -> displayListOfCommands();
                case "quit", "/q" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands() {
        insertControlChars('n', 1);
        message("·  Generate password [" + getColor(sysMainColor) + "/gp"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Restart [" + getColor(sysMainColor)
                + "/rs" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Clear terminal [" + getColor(sysMainColor)
                + "/cl" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  List [" + getColor(sysMainColor)
                + "/ls" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Quit [" + getColor(sysMainColor)
                + "/q" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    private static void passwordCreatorMenu() {
        insertControlChars('n', 1);
        out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "Enter length of password [1-80]: ");
        try {
            passwordLength = scanner.nextInt();
            if (passwordLength <= 0 || passwordLength > 80) {
                message("Invalid password length. Please enter a number between 1 and 80.",
                        sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                return;
            }
        } catch (Exception e) {
            message("Invalid input. Please enter a number.", sysLayoutColor, getDefaultTextAlignment(),
                    getDefaultDelay(), out::print);
            scanner.nextLine();
            return;
        }

        scanner.nextLine();

        insertControlChars('n', 1);
        out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "Password complexity ["
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
            insertControlChars('n', 1);
            message("Generated Password: " + generatedPassword,
                    sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

            displayConfirmation("Enter","y","+",
                    "to open and","n","-","to skip",
                    sysAcceptanceColor, sysRejectionColor, sysLayoutColor,getDefaultTextAlignment());

            choice("Copy to clipboard",copyToClipboard(generatedPassword),sysLayoutColor,sysLayoutColor,sysRejectionColor);
            insertControlChars('n', 1);
        } else {
            message("Invalid complexity option. Please try again.", sysLayoutColor, getDefaultTextAlignment(),
                    getDefaultDelay(), out::println);
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