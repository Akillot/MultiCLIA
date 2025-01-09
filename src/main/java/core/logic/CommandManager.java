package core.logic;

import core.command_handling_system.PackageUnifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.net.URI;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static core.logic.AppearanceConfigs.*;
import static core.logic.DisplayManager.*;

import static core.logic.TextConfigs.*;
import static java.lang.System.exit;
import static java.lang.System.out;

public class CommandManager {

    public static void switchLogoRandomly(String[] logo, int alignment) {
        int indexOfLogo = getRandomIndexWithProbability();
        switchLogoManually(logo, indexOfLogo, alignment);
    }

    public static void switchLogoManually(String @NotNull [] logo, int indexOfLogo, int alignment) {
        String[] colors = getColorsForLogo(indexOfLogo);

        for (int i = 0; i < logo.length; i++) {
            String coloredText = colors[i % colors.length] + logo[i] + RESET;
            message(coloredText, systemLayoutColor, alignment, 0, System.out::print);
        }
    }

    private static String @NotNull [] getColorsForLogo(int indexOfLogo) {
        return switch (indexOfLogo) {
            case 0 -> new String[]{
                    getAnsi256Color(99), getAnsi256Color(56),
                    getAnsi256Color(165), getAnsi256Color(99),
                    getAnsi256Color(63), getAnsi256Color(99)};

            case 1 -> new String[]{
                    getAnsi256Color(140), getAnsi256Color(98),
                    getAnsi256Color(134), getAnsi256Color(129),
                    getAnsi256Color(93), getAnsi256Color(171)};

            case 2 -> new String[]{
                    getAnsi256Color(84), getAnsi256Color(114),
                    getAnsi256Color(77), getAnsi256Color(48),
                    getAnsi256Color(83), getAnsi256Color(76)};

            case 3 -> new String[]{
                    getAnsi256Color(153), getAnsi256Color(110),
                    getAnsi256Color(75), getAnsi256Color(189),
                    getAnsi256Color(223), getAnsi256Color(210)};

            case 4 -> new String[]{
                    getAnsi256Color(219), getAnsi256Color(183),
                    getAnsi256Color(147), getAnsi256Color(218),
                    getAnsi256Color(182), getAnsi256Color(218)};

            default -> new String[]{
                    getAnsi256Color(systemLayoutColor), getAnsi256Color(systemLayoutColor),
                    getAnsi256Color(systemLayoutColor), getAnsi256Color(systemLayoutColor),
                    getAnsi256Color(systemLayoutColor), getAnsi256Color(systemLayoutColor)};
        };
    }

    private static int getRandomIndexWithProbability() {
        double[] probabilities = {5, 5, 5, 5, 80};
        double randomValue = new Random().nextDouble() * 100;
        double cumulativeProbability = 0;

        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (randomValue < cumulativeProbability) {
                return i;
            }
        }
        return probabilities.length - 1;
    }

    //Searching
    public static void searchCommands() {
        PackageUnifier registry = new PackageUnifier();
        try {
            slowMotionText(0, 56, false,
                    getAnsi256Color(systemLayoutColor) + "> ", "");
            String nameOfFunction = scanner.nextLine().toLowerCase();
            if (!registry.executeCommand(nameOfFunction)) {
                searchCommands();
            }
        }
        catch(Exception e){
            out.print("");
        }
    }

    @Contract(pure = true)
    public static @NotNull Runnable openUri(String userSite) {
        return () -> {
            try {
                URI uri = new URI(userSite);
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(uri);
                    message("\r   Status: " + getAnsi256Color(systemAcceptanceColor) + "✓", systemLayoutColor,58,0,out::print);
                } else {
                    message("Error: Desktop or browse action not supported", systemRejectionColor,
                            58, 0, out::print);
                    message("Status: " + getAnsi256Color(systemRejectionColor) + "x", systemLayoutColor, 58, 0, out::print);
                }
            } catch (URISyntaxException | IOException e) {
                message("Error opening URL", systemLayoutColor, 58, 0, out::print);
                message("Status: " + getAnsi256Color(systemRejectionColor) + "x", systemLayoutColor, 58, 0, out::print);
            }
        };
    }

    public static void choice(String title, Runnable action, int mainColor, int layoutColor, int rejectionColor) {
        out.print(alignment(58) + getAnsi256Color(mainColor) + title + RESET + ": " + RESET);

        String choice = scanner.nextLine().toLowerCase();
        switch (choice) {
            case "+":
            case "y":
                try {
                    action.run();
                } catch (Exception e) {
                    message("Error executing action", rejectionColor, 58, 0, out::print);
                    message("Status: " + getAnsi256Color(rejectionColor) + "x", layoutColor, 58, 0, out::print);
                }
                break;

            case "-":
            case "n":
                message("Status: " + getAnsi256Color(rejectionColor) + "x", layoutColor, 58, 0, out::print);
                break;

            default:
                message("Invalid choice", rejectionColor, 58, 0, out::print);
                message("Status: " + getAnsi256Color(rejectionColor) + "x",
                        layoutColor, 58, 0, out::print);
                break;
        }
    }

    public static void terminate(int themeColor_1, int acceptanceColor, int layoutColor) {
        message("\r   Status: " + getAnsi256Color(acceptanceColor) + "✓", layoutColor,58,0,out::print);
        message("Terminated correctly", themeColor_1,
                58,0,out::print);
        modifyMessage('n',2);
        border();
    }

    public static void terminateProgram() {
        marginBorder(1,2);
        message("\r    Status: " + getAnsi256Color(systemAcceptanceColor) + "✓", systemLayoutColor,58,0,out::print);
        message("Program terminated quickly correctly", systemMainColor,
                56,0,out::print);
        modifyMessage('n', 2);
        exit(0);
    }
}