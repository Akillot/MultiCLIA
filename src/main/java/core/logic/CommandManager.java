package core.logic;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import static core.logic.BorderFunc.marginBigBorder;
import static core.logic.ColorFunc.*;
import static core.logic.DisplayManager.*;
import static core.logic.TextFunc.alignment;

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
                    messageModifier('n', 1);
                } else {
                    message("Error: Desktop or browse action not supported", "red",
                            58, 0, System.out::print);
                }
            } catch (URISyntaxException | IOException e) {
                message("Error opening URL", "red", 58, 0, System.out::print);
            }
        };
    }

    public static void getUserLocalIp(){
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            out.println(alignment(58) + WHITE + BOLD + "Your local IP: " + RESET + BLUE + localHost + RESET);
        } catch (UnknownHostException e) {
            errorAscii();
            message("IP is undefined", "red", 58,0,out::println);
        }
    }

    public static void getUserExternalIp() {
        try {
            URI uri = new URI("https://api.ipify.org");
            URL url = uri.toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String ip = reader.readLine();
            reader.close();
            out.println(alignment(58) + WHITE + BOLD + "Your external IP: " + RESET + BLUE + ip + RESET);

        } catch (Exception e) {
            message("Error: " + e.getMessage(), "red", 58,0,out::println);
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
                    out.println("Error executing action");
                }
                break;

            case "-":
                message("Skipped", "white", 58,0,out::print);
                messageModifier('n', 1);
                break;

            default:
                message("Invalid choice", "red", 58,0,out::print);
                messageModifier('n', 1);
                break;
        }
    }

    public static void exitExtension() {
        messageModifier('n', 1);
        loadingAnimation(300, 10);
        message(alignment(58) + RED + BOLD + "Application exit" + RESET,"red",
                100,0,out::print);
        marginBigBorder();
        messageModifier('n', 1);
    }

    public static void exitProgramDefault() {
        messageModifier('n', 1);
        loadingAnimation(300, 10);
        message("\r    Status: ✓", "white", 58,0,out::print);
        message("Program termination","red",
                56,20,out::print);
        messageModifier('n', 1);
        exit(0);
    }

    public static void exitProgramQuick() {
        messageModifier('n', 1);
        message("\r    Status: ✓", "white", 58,0,out::print);
        message("Quick program termination" + RESET,"red",
                56,0,out::print);
        messageModifier('n', 1);
        exit(0);
    }
}