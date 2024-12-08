package core.logic;

import core.command_handling_system.PackageUnifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.net.URI;

import static core.logic.BorderConfigs.borderWidth;
import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.DisplayManager.*;

import static core.logic.TextConfigs.*;
import static java.lang.System.exit;
import static java.lang.System.out;

public class CommandManager {

    public static void switchLogo(String[] logo, int alignment) {
        String[] colors;
        int indexOfLogo = rand.nextInt(0,3);

        switch (indexOfLogo) {
            case 0 -> colors = new String[]{
                    getAnsi256Color(systemDefaultColor), getAnsi256Color(56),
                    getAnsi256Color(165), getAnsi256Color(99),
                    getAnsi256Color(63), getAnsi256Color(99)};

            case 1 -> colors = new String[]{
                    getAnsi256Color(140), getAnsi256Color(98),
                    getAnsi256Color(134), getAnsi256Color(129),
                    getAnsi256Color(93), getAnsi256Color(171)};

            case 2 -> colors = new String[]{
                    getAnsi256Color(132), getAnsi256Color(168),
                    getAnsi256Color(204), getAnsi256Color(133),
                    getAnsi256Color(169), getAnsi256Color(205)};

            default -> colors = new String[]{
                    getAnsi256Color(99), getAnsi256Color(systemDefaultWhite),
                    getAnsi256Color(systemDefaultWhite), getAnsi256Color(systemDefaultWhite),
                    getAnsi256Color(systemDefaultWhite), getAnsi256Color(systemDefaultWhite)};
        }

        for (int i = 0; i < logo.length; i++) {
            String coloredText = colors[i % colors.length] + logo[i] + RESET;
            message(coloredText, i % colors.length, alignment, 0, System.out::print);
        }
    }

    public static void searchCommands() {
        PackageUnifier registry = new PackageUnifier();
        try {
            slowMotionText(50, 56, false, true,
                    getAnsi256Color(systemDefaultWhite) + "Search: ", "");
            String nameOfFunction = scanner.nextLine().toLowerCase();
            modifyMessage('n', 1);
            wrapText(nameOfFunction, borderWidth - 2);

            if (!registry.executeCommand(nameOfFunction)) {
                modifyMessage('n', 2);
                errorAscii();
                marginBorder();
            }
        }
        catch (Exception e) {
            modifyMessage('n', 2);
            errorAscii();
            marginBorder();
        }
    }

    @Contract(pure = true)
    public static @NotNull Runnable openUri(String userSite) {
        return () -> {
            try {
                URI uri = new URI(userSite);
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(uri);
                    message("Status: ✓",systemDefaultWhite,58,0,out::print);
                } else {
                    message("Error: Desktop or browse action not supported",systemDefaultRed,
                            58, 0,out::print);
                    message("Status: x",systemDefaultWhite,58,0,out::print);
                }
            } catch (URISyntaxException | IOException e) {
                message("Error opening URL",systemDefaultRed,58,0,out::print);
                message("Status: x",systemDefaultWhite,58,0,out::print);
            }
        };
    }

    public static void getUserLocalIp(){
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            out.println(alignment(58) + getAnsi256Color(systemDefaultWhite) + BOLD + "Your local IP: " + RESET
                    + getAnsi256Color(systemDefaultColor) + localHost + RESET);
        } catch (UnknownHostException e) {
            errorAscii();
            message("IP is undefined",systemDefaultRed,58,0,out::print);
            message("Status: x",systemDefaultWhite,58,0,out::print);
        }
    }

    public static void choice(String title, Runnable action) {
        out.print(alignment(58) + getAnsi256Color(systemDefaultColor) +
                BOLD + title + RESET +  BOLD + ": " + RESET);

        String choice = scanner.nextLine().toLowerCase();
        switch (choice) {
            case "+":
                try {
                    action.run();
                    modifyMessage('n', 1);
                } catch (Exception e) {
                    message("Error executing action",systemDefaultRed,58,0,out::print);
                    message("Status: x",systemDefaultWhite,58,0,out::print);
                }
                break;

            case "-":
                message("Status: x",systemDefaultWhite,58,0,out::print);
                modifyMessage('n',1);
                break;

            default:
                message("Invalid choice",systemDefaultRed,58,0,out::print);
                message("Status: x",systemDefaultWhite,58,0,out::print);
                modifyMessage('n',1);
                break;
        }
    }

    public static void terminateExtension() {
        message("\r   Status: ✓",systemDefaultWhite,58,0,out::print);
        message("Extension terminated correctly",systemDefaultColor,
                58,0,out::print);
        modifyMessage('n',1);
        marginBorder();
    }

    public static void terminateProgramDefault() {
        modifyMessage('n',1);
        loadingAnimation(300,10);
        message("\r    Status: ✓",systemDefaultWhite,58,0,out::print);
        message("Program terminated correctly",systemDefaultColor,
                56,20,out::print);
        modifyMessage('n', 1);
        exit(0);
    }

    public static void terminateProgramQuick() {
        modifyMessage('n',1);
        message("\r    Status: ✓", systemDefaultWhite,58,0,out::print);
        message("Program terminated quickly correctly",systemDefaultColor,
                56,0,out::print);
        modifyMessage('n', 1);
        exit(0);
    }
}