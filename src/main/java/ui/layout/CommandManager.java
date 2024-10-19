package ui.layout;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static ui.layout.BorderFunc.displayMarginBigBorder;
import static ui.layout.ColorFunc.*;
import static ui.layout.DisplayManager.*;
import static ui.layout.TextFunc.alignment;

public class CommandManager {
    public static void openUri(String userSite) {
        try {
            URI uri = new URI(userSite);
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
                System.out.print("\r   Opened in browser\n");
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
        exitMessage(alignment(58) + RED + BOLD + "Application exit" + RESET);
        System.out.println(RED + "...\n" + RESET);
        displayMarginBigBorder();
        System.out.print("\n");
    }

    public static void exitProgram() {
        System.out.print("\n");
        exitMessage(alignment(58) + RED + "Program exit" + RESET);
        System.out.println(RED + "..." + RESET);
        System.out.print("\n");
        System.exit(0);
    }
}
