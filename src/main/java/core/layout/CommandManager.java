package core.layout;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;

import static core.layout.BorderFunc.displayMarginBigBorder;
import static core.layout.ColorFunc.*;
import static core.layout.DisplayManager.*;
import static core.layout.TextFunc.alignment;

public class CommandManager {
    public static void openUri(String userSite) {
        try {
            URI uri = new URI(userSite);
            String host = uri.getHost();

            if (host != null && host.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
                InetAddress inetAddress = InetAddress.getByName(host);

                if (inetAddress.isReachable(3000)) {
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(uri);
                        System.out.print(WHITE + BOLD + "\r   Opened in browser\n" + RESET);
                        System.out.print("\n");
                    } else {
                        errorAscii();
                        displayMarginBigBorder();
                    }
                } else {
                    System.out.print("\n");
                    displayMarginBigBorder();
                    errorAscii();
                    message("IP address is unreachable", "red", 58);
                    System.out.print("\n");
                }
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

    public static void exitExtension() {
        System.out.print("\n");
        exitMessage(alignment(58) + RED + BOLD + "Application exit" + RESET, 100);
        System.out.println(RED + "...\n" + RESET);
        displayMarginBigBorder();
        System.out.print("\n");
    }

    public static void exitProgramDefault() {
        System.out.print("\n");
        loadingAnimation(300, 10);
        exitMessage(alignment(62) + RED + "Program termination" + RESET, 50);
        System.out.print("\n");
        System.exit(0);
    }

    public static void exitProgramQuick() {
        System.out.print("\n");
        System.out.print(WHITE + BOLD + "\r    ✓" + RESET);
        exitMessage(alignment(62) + RED + "Quick program termination" + RESET, 0);
        System.out.print("\n");
        System.exit(0);
    }
}
