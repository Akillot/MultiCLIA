package core.ui.extensions.security;

import core.ui.essential.pages.Page;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.*;
import java.util.Random;

import static core.logic.CommandManager.*;
import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.ui.essential.configs.DisplayManager.scanner;
import static core.ui.essential.configs.appearance.TextConfigs.*;
import static java.lang.System.out;

public class SecurityPage extends Page {

    private static final int easyComplexityColor = 85;
    private static final int mediumComplexityColor = 214;
    private static final int strongComplexityColor = 177;
    private static final int extraComplexityColor = 201;

    private static final String CHAR_POOL_EASY = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_POOL_MEDIUM = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String CHAR_POOL_STRONG = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";
    private static final String CHAR_POOL_EXTRA = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789<>.,/|\\?!+-*&^%$#@!~'}{)(";

    private static int passwordLength;

    private String[][] commands = {
            {"Generate password", "gp"},
            {"Restart", "rst"},
            {"Restart clear", "rcl"},
            {"Clear", "cl"},
            {"Help", "h"},
            {"Quit", "q"}
    };

    public void displayMenu() {
        Security.addProvider(new BouncyCastleProvider());
        marginBorder(1, 2);
        message("Security:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands(commands);

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false, getColor(layoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "generate password", "gp" -> passwordCreatorMenu();
                case "restart", "rst" -> {
                    insertControlChars('n',1);
                    mainMenuRestart();
                }
                case "restart clear", "rcl" -> mainMenuRestartWithClearing();
                case "clear", "cl" -> clearTerminal();
                case "help", "h" -> displayListOfCommands(commands);
                case "quit", "q", "exit", "e" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    @Override
    protected void displayListOfCommands(String[][] commands) {
        super.displayListOfCommands(commands);
    }

    private static void passwordCreatorMenu() {
        insertControlChars('n', 1);
        out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter length of password [1-80]: ");
        try {
            passwordLength = scanner.nextInt();
            if (passwordLength <= 0 || passwordLength > 80) {
                message("Invalid password length. Please enter a number between 1 and 80.",
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                return;
            }
        } catch (Exception e) {
            message("Invalid input. Please enter a number.", layoutColor, getDefaultTextAlignment(),
                    getDefaultDelay(), out::println);
            scanner.nextLine();
            return;
        }

        scanner.nextLine();

        insertControlChars('n', 1);
        out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Password complexity ["
                + getColor(easyComplexityColor) + "light" + getColor(layoutColor) + "|"
                + getColor(easyComplexityColor) + "1" + getColor(layoutColor) + ", "
                + getColor(mediumComplexityColor) + "medium" + getColor(layoutColor) + "|"
                + getColor(mediumComplexityColor) + "2" + getColor(layoutColor) + ", "
                + getColor(strongComplexityColor) + "strong" + getColor(layoutColor) + "|"
                + getColor(strongComplexityColor) + "3" + getColor(layoutColor) + ", "
                + getColor(extraComplexityColor) + "extra" + getColor(layoutColor) + "|"
                + getColor(extraComplexityColor) + "4" + getColor(layoutColor) + "]: ");

        String passwordComplexity = scanner.nextLine().toLowerCase();
        String generatedPassword = createPassword(passwordComplexity);
        if (generatedPassword != null) {
            insertControlChars('n', 1);
            message("Generated Password: " + generatedPassword,
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

            displayConfirmation("Enter","y","+",
                    "to open and","n","-","to skip",
                    acceptanceColor, rejectionColor, layoutColor,getDefaultTextAlignment());

            choice("Copy to clipboard",copyToClipboard(generatedPassword), layoutColor, layoutColor, rejectionColor);
            insertControlChars('n', 1);
        } else {
            message("Invalid complexity option. Please try again.", layoutColor, getDefaultTextAlignment(),
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