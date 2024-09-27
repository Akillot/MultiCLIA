package ui.layout;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static ui.layout.BorderWork.drawTripleBorder;
import static ui.layout.ColorWork.*;
import static ui.layout.TextWork.contentAlignment;
import static ui.layout.ThemesWork.displayErrorAscii;

public class BasicFunctions {

    public static Scanner scanner = new Scanner(System.in);
    private static int delay;

    public static void displayTip(String text, int alignment) {
        System.out.println(contentAlignment(alignment) + BOLD
                + "[" + WHITE + BOLD + "i" + RESET
                + BOLD + "] " + text + RESET);
    }

    private static void displayExitText(String exitText, int delay) {
        for (char ch : exitText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayErrorAscii();
                displayColorMessage("Error, try again", "red", 0);
            }
        }
    }

    public static void exitApp() {
        displayExitMessage(contentAlignment(18) + RED + BOLD + "Application exit" + RESET, 150);
        System.out.println(RED + "...\n" + RESET);
        drawTripleBorder();
        System.out.print("\n");
    }

    public static void exitMultiClia() {
        System.out.print("\n");
        displayExitMessage(contentAlignment(58) + RED + "Program exit" + RESET, 150);
        System.out.println(RED + "..." + RESET);
        System.exit(0);
    }

    private static void displayExitMessage(String message, int delay) {
        displayExitText(message, delay);
    }

    public static void displayListOfMenuCommands() {
        String[] commands = {"calculator", "browser", "settings", "commands", "info", RED + "exit" + RESET};
        for (String command : commands) {
            System.out.println(contentAlignment(58) + "· " + command);
        }
        drawTripleBorder();
        System.out.print("\n");
    }

    public static void openSite(String userSite) {
        try {
            URI uri = new URI(userSite);
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
                System.out.println("\r   Opened in browser\n");
            } else {
                displayErrorAscii();
                System.out.println("Browsing action not supported. Please manually open the link: " + userSite);
            }
        } catch (URISyntaxException | IOException e) {
            System.out.print("\n");
            drawTripleBorder();
            System.out.print("\n");
            displayErrorAscii();
            displayColorMessage("Failed to open link: " + e.getMessage(), "red", 0);
        } catch (Exception e) {
            displayErrorAscii();
            displayColorMessage("Unexpected error: " + e.getMessage(), "red", 0);
        }
    }
}
