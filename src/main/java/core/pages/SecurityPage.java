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
import static java.lang.System.out;

public class SecurityPage {
    public static void securityMenu(){

    }

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
}
