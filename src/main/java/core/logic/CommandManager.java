package core.logic;

import core.command_handling_system.PackageUnifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.net.URI;
import java.util.Random;

import static core.logic.AppearanceConfigs.*;
import static core.logic.DisplayManager.*;

import static core.logic.TextConfigs.*;
import static java.lang.System.exit;
import static java.lang.System.out;

public class CommandManager {

    //displaying logo
    public static void switchLogoRandomly(String[] logo, int alignment) {
        Random rand = new Random();
        int indexOfLogo = rand.nextInt(0, 5);
        switchLogoManually(logo, indexOfLogo, alignment);
    }

    public static void switchLogoManually(String[] logo, int indexOfLogo, int alignment) {
        String[] colors;
        switch (indexOfLogo) {
            case 0 -> colors = new String[]{
                    getAnsi256Color(99), getAnsi256Color(56),
                    getAnsi256Color(165), getAnsi256Color(99),
                    getAnsi256Color(63), getAnsi256Color(99)};

            case 1 -> colors = new String[]{
                    getAnsi256Color(140), getAnsi256Color(98),
                    getAnsi256Color(134), getAnsi256Color(129),
                    getAnsi256Color(93), getAnsi256Color(171)};

            case 2 -> colors = new String[]{
                    getAnsi256Color(84), getAnsi256Color(114),
                    getAnsi256Color(77), getAnsi256Color(48),
                    getAnsi256Color(83), getAnsi256Color(76)};

            case 3 -> colors = new String[]{
                    getAnsi256Color(153), getAnsi256Color(110),
                    getAnsi256Color(75), getAnsi256Color(189),
                    getAnsi256Color(223), getAnsi256Color(210)};

            case 4 -> colors = new String[]{
                    getAnsi256Color(219), getAnsi256Color(183),
                    getAnsi256Color(147), getAnsi256Color(218),
                    getAnsi256Color(182), getAnsi256Color(218)};

            default -> colors = new String[]{
                    getAnsi256Color(systemLayoutColor), getAnsi256Color(systemLayoutColor),
                    getAnsi256Color(systemLayoutColor), getAnsi256Color(systemLayoutColor),
                    getAnsi256Color(systemLayoutColor), getAnsi256Color(systemLayoutColor)};
        }

        for (int i = 0; i < logo.length; i++) {
            String coloredText = colors[i % colors.length] + logo[i] + RESET;
            message(coloredText, i % colors.length, alignment, 0, System.out::print);
        }
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
            modifyMessage('n', 2);
            displayErrorAscii();
            marginBorder(1, 1);
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

    public static void getUserLocalIp() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            out.println(alignment(58) + getAnsi256Color(systemLayoutColor) + "Your local IP: " + RESET
                    + getAnsi256Color(systemMainColor) + localHost + RESET);
        } catch (UnknownHostException e) {
            displayErrorAscii();
            message("IP is undefined", systemRejectionColor, 58, 0, out::print);
            message("Status: " + getAnsi256Color(systemRejectionColor) + "x", systemLayoutColor, 58, 0, out::print);
        }
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

    public static boolean executeWithProbability(double probabilityPercentage) throws IllegalArgumentException {
        if (probabilityPercentage < 0 || probabilityPercentage > 100) {
           message("Probability must be between 0 and 100.",
                   systemRejectionColor,58,0,out::print);
        }
        Random rand = new Random();
        double randomValue = rand.nextDouble() * 100;
        return randomValue < probabilityPercentage;
    }

    public static void clearTerminal(){
        try {
            String operatingSystem = System.getProperty("os.name");
            if (operatingSystem.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            message("Error executing action", systemRejectionColor, 58, 0, out::print);
            message("Status: " + getAnsi256Color(systemRejectionColor) + "x", systemLayoutColor, 58, 0, out::print);
        }
    }

    public static void terminate(int themeColor_1, int acceptanceColor, int layoutColor) {
        message("\r   Status: " + getAnsi256Color(acceptanceColor) + "✓", layoutColor,58,0,out::print);
        message("Terminated correctly", themeColor_1,
                58,0,out::print);
        modifyMessage('n',2);
        border();
    }

    public static void terminateProgramDefault() {
        modifyMessage('n',2);
        loadingAnimation(300,10);
        message("\r    Status: " + getAnsi256Color(systemAcceptanceColor) + "✓", systemLayoutColor,58,0,out::print);
        message("Program terminated correctly", systemMainColor,
                56,20,out::print);
        modifyMessage('n', 2);
        exit(0);
    }

    public static void terminateProgramQuick() {
        modifyMessage('n',2);
        message("\r    Status: " + getAnsi256Color(systemAcceptanceColor) + "✓", systemLayoutColor,58,0,out::print);
        message("Program terminated quickly correctly", systemMainColor,
                56,0,out::print);
        modifyMessage('n', 2);
        exit(0);
    }
}