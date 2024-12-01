package core.logic;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.net.URI;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.alignment;

import static java.lang.System.exit;
import static java.lang.System.out;

public class CommandManager {
    public static Runnable openUri(String userSite) {
        return () -> {
            try {
                URI uri = new URI(userSite);
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(uri);
                    message("Status: ✓","white", 58,0, System.out::print);
                } else {
                    message("Error: Desktop or browse action not supported", "red",
                            58, 0, System.out::print);
                    message("Status: x","white", 58,0, System.out::print);
                }
            } catch (URISyntaxException | IOException e) {
                message("Error opening URL", "red", 58, 0, System.out::print);
                message("Status: x","white", 58,0, System.out::print);
            }
        };
    }

    public static void getUserLocalIp(){
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            out.println(alignment(58) + WHITE + BOLD + "Your local IP: " + RESET + BLUE + localHost + RESET);
        } catch (UnknownHostException e) {
            errorAscii();
            message("IP is undefined", "red", 58,0,out::print);
            message("Status: x", "white", 58,0,out::print);
        }
    }

    public static void choice(String title, Runnable action) {
        out.print(alignment(58) + BLUE + BOLD + title + RESET + BOLD + ": " + RESET);

        String choice = scanner.nextLine().toLowerCase();
        switch (choice) {
            case "+":
                try {
                    action.run();
                    messageModifier('n', 1);
                } catch (Exception e) {
                    message("Error executing action", "red", 58,0,out::print);
                    message("Status: x", "white", 58,0,out::print);
                }
                break;

            case "-":
                message("Status: x", "white", 58,0,out::print);
                messageModifier('n', 1);
                break;

            default:
                message("Invalid choice", "red", 58,0,out::print);
                message("Status: x", "white", 58,0,out::print);
                messageModifier('n', 1);
                break;
        }
    }

    public static void terminateExtension() {
        messageModifier('n', 1);
        message("\r   Status: ✓", "white", 58,0,out::print);
        message(alignment(58) + RED + BOLD + "Extension terminated correctly" + RESET,"blue",
                100,0,out::print);
        marginBorder();
        messageModifier('n', 1);
    }

    public static void terminateProgramDefault() {
        messageModifier('n', 1);
        loadingAnimation(300, 10);
        message("\r    Status: ✓", "white", 58,0,out::print);
        message("Program terminated correctly","blue",
                56,20,out::print);
        messageModifier('n', 1);
        exit(0);
    }

    public static void terminateProgramQuick() {
        messageModifier('n', 1);
        message("\r    Status: ✓", "white", 58,0,out::print);
        message("Program terminated quickly correctly" + RESET,"blue",
                56,0,out::print);
        messageModifier('n', 1);
        exit(0);
    }
}