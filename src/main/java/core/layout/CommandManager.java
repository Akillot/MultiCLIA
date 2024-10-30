package core.layout;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import static core.layout.BorderFunc.displayMarginBigBorder;
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
                out.print("\r   Opened in browser\n");
            } else {
                errorAscii();
            }
        } catch (URISyntaxException | IOException e) {
            displayMarginBigBorder();
            errorAscii();
        } catch (Exception e) {
            errorAscii();
        }
    }
    public static void getUserIp(){
        out.print("\n\n");
        getUserLocalIp();
        getUserExternalIp();
        out.print("\n");
        displayMarginBigBorder();
    }

    public static void getUserLocalIp(){
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            message("Your local IP: " + localHost.getHostAddress(), "white", 58);
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
            message("Your external IP: " + ip, "white", 58);

        } catch (Exception e) {
            message("Error: " + e.getMessage(), "red", 58);
        }
    }


    public static void exitExtension() {
        out.print("\n");
        exitMessage(alignment(58) + RED + BOLD + "Application exit" + RESET, 100);
        out.println(RED + "...\n" + RESET);
        displayMarginBigBorder();
        out.print("\n");
    }

    public static void exitProgramDefault() {
        out.print("\n");
        loadingAnimation(300, 10);
        exitMessage(alignment(62) + RED + "Program termination" + RESET, 50);
        out.print("\n");
        exit(0);
    }

    public static void exitProgramQuick() {
        out.print("\n");
        out.print(WHITE + BOLD + "\r    ✓" + RESET);
        exitMessage(alignment(62) + RED + "Quick program termination" + RESET, 0);
        out.print("\n");
        exit(0);
    }
}
