package ui.layout;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.util.Scanner;

import static commands_language_packages.CommandHandler.*;
import static ui.layout.BorderFunc.drawTripleBorder;
import static ui.layout.ColorFunc.*;
import static ui.layout.TextFunc.contentAlignment;
import static ui.layout.ThemesFunc.displayErrorAscii;

public class BasicFunc {

    public static Scanner scanner = new Scanner(System.in);

    public static void displayTip(String text, int alignment) {
        System.out.println(contentAlignment(alignment) + BOLD
                + "[" + WHITE + BOLD + "i" + RESET
                + BOLD + "] " + text + RESET);
    }

    public static void displayLangs(){
        String[] langs = new String[]{
                "· English", "· Čeština", "· Deutsch",
                "· Русский", "· Français", "· Español",
                "· Toki Pona"};
        for(String lang : langs){
            displayContent(lang,"white", 58);
        }
        System.out.print("\n");
        drawTripleBorder();
        System.out.print("\n");
    }

    public static void displayAllLangCommands() {
        String[][] commandPacks = {
                calculatorCommands, basicFunctionsCommands, timeCommands,
                browserCommands, infoCommands, notepadCommands, langsCommands,
                exitCommands, allLangCommands
        };

        for (String[] commandPack : commandPacks) {
            for (int i = 0; i < commandPack.length; i++) {
                if (i == 0) {
                    displayContent(commandPack[i], "purple", 58);
                } else {
                    displayContent(commandPack[i], "white", 58);
                }
            }
            System.out.print("\n");
            displayContent("--------------------", "white", 58);
            System.out.print("\n");
        }

        System.out.print("\n");
        drawTripleBorder();
        System.out.print("\n");
    }


    private static void displayExitText(String exitText, int delay) {
        for (char ch : exitText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayErrorAscii();
                displayContent("Error, try again", "red", 0);
            }
        }
    }

    public static void exitApp() {
        displayExitMessage(contentAlignment(58) + RED + BOLD + "Application exit" + RESET);
        System.out.println(RED + "...\n" + RESET);
        drawTripleBorder();
        System.out.print("\n");
    }

    public static void exitMultiClia() {
        System.out.print("\n");
        displayExitMessage(contentAlignment(58) + RED + "Program exit" + RESET);
        System.out.println(RED + "..." + RESET);
        System.exit(0);
    }

    private static void displayExitMessage(String message) {
        displayExitText(message, 100);
    }

    public static void displayListOfMenuCommands() {
        String[] commands = {"calculator", "browser", "notepad", "commands", "info", "languages" ,BOLD + RED + "exit" + RESET};
        for (String command : commands) {
            displayContent("· " + command, "white", 58);
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
            displayContent("Failed to open link: " + e.getMessage(), "red", 0);
        } catch (Exception e) {
            displayErrorAscii();
            displayContent("Unexpected error: " + e.getMessage(), "red", 0);
        }
    }
}
