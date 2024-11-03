package core.layout;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import static core.layout.BorderFunc.marginBigBorder;
import static core.layout.ColorFunc.*;
import static core.layout.DisplayManager.*;
import static core.layout.TextFunc.alignment;

import static java.lang.System.exit;
import static java.lang.System.out;

public class CommandManager {
    public static void openUri(String userSite) {
        try {
            URI uri = new URI(userSite);
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
                out.print("\r   Opened in browser");
                messageModifier('n', 1);
            } else {
                message("Error: Desktop or browse action not supported", "red", 58);
            }
        } catch (URISyntaxException | IOException e) {
            message("Error opening URL", "red", 58);
        }
    }

    public static Runnable getOpenUriAction(String userSite) {
        return () -> openUri(userSite);
    }

    public static void getUserIp(){
        messageModifier('n', 2);
        getUserLocalIp();
        getUserExternalIp();
        messageModifier('n', 1);
        marginBigBorder();
    }

    public static void getUserLocalIp(){
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            out.println(alignment(58) + WHITE + BOLD + "Your local IP: " + RESET + PURPLE + localHost + RESET);
        } catch (UnknownHostException e) {
            errorAscii();
            message("IP is undefined", "red", 58);
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
            out.println(alignment(58) + WHITE + BOLD + "Your external IP: " + RESET + PURPLE + ip + RESET);

        } catch (Exception e) {
            message("Error: " + e.getMessage(), "red", 58);
        }
    }

    public static void choice(String title, Runnable action) {
        message("Enter '+' to open and '-' to skip", "white", 58);
        messageModifier('n', 1);
        out.print(alignment(58) + PURPLE + BOLD + title + RESET + BOLD + ": " + RESET);

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
                message("Alright, next time", "white", 58);
                messageModifier('n', 1);
                break;

            default:
                message("Invalid choice", "red", 58);
                messageModifier('n', 1);
                break;
        }
    }

    public static void exitExtension() {
        messageModifier('n', 1);
        loadingAnimation(300, 10);
        exitMessage(alignment(58) + RED + BOLD + "Application exit" + RESET, 100);
        marginBigBorder();
        messageModifier('n', 1);
    }

    public static void exitProgramDefault() {
        messageModifier('n', 1);
        loadingAnimation(300, 10);
        exitMessage(alignment(62) + RED + "Program termination" + RESET, 50);
        messageModifier('n', 1);
        exit(0);
    }

    public static void exitProgramQuick() {
        messageModifier('n', 1);
        out.print(WHITE + BOLD + "\r    ✓" + RESET);
        exitMessage(alignment(62) + RED + "Quick program termination" + RESET, 0);
        messageModifier('n', 1);
        exit(0);
    }
}
