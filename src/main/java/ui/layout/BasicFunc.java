package ui.layout;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static commands_language_packages.CommandHandler.*;
import static ui.layout.BorderFunc.*;
import static ui.layout.ColorFunc.*;
import static ui.layout.TextFunc.alignmentLogic;
import static ui.layout.ThemesFunc.displayErrorAscii;

public class BasicFunc {

    public static Scanner scanner = new Scanner(System.in);

    public static void displayTip(String text, int alignment) {
        System.out.println(alignmentLogic(alignment) + BOLD
                + "[" + WHITE + BOLD + "i" + RESET
                + BOLD + "] " + text + RESET);
    }

    public static void displayLangs(){
        System.out.print("\n");
        System.out.print("\n");
        String[] langs = new String[]{
                "· English", "· Čeština", "· Deutsch",
                "· Русский", "· Français", "· Español",
                "· Toki Pona"};
        for(String lang : langs){
            displayContent(lang,"white", 58);
        }
        displayMarginBigBorder();
    }

    public static void displayAllLangCommands() {
        String[][] commandPacks = {
                calculatorCommands, basicFunctionsCommands, timeCommands,
                browserCommands, infoCommands, notepadCommands, langsCommands,
                exitCommands, allLangCommands
        };
        System.out.print("\n");
        System.out.print("\n");
        for (int packIndex = 0; packIndex < commandPacks.length; packIndex++) {
            String[] commandPack = commandPacks[packIndex];
            for (int i = 0; i < commandPack.length; i++) {
                if (i == 0) {
                    displayContent(commandPack[i], "purple", 58);
                } else {
                    displayContent(commandPack[i], "white", 58);
                }
            }

            if (packIndex < commandPacks.length - 1) {
                System.out.print("\n");
                displayContent("--------------------", "white", 58);
                System.out.print("\n");
            }
        }
        displayMarginBigBorder();
    }

    private static void displayExitText(String exitText) {
        for (char ch : exitText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                displayErrorAscii();
                displayContent("Error, try again", "red", 0);
            }
        }
    }

    public static void exitApp() {
        displayExitMessage(alignmentLogic(58) + RED + BOLD + "Application exit" + RESET);
        System.out.println(RED + "...\n" + RESET);
        displayMarginBigBorder();
        System.out.print("\n");
    }

    public static void exitMultiClia() {
        System.out.print("\n");
        displayExitMessage(alignmentLogic(58) + RED + "Program exit" + RESET);
        System.out.println(RED + "..." + RESET);
        System.out.print("\n");
        System.exit(0);
    }

    private static void displayExitMessage(String message) {
        displayExitText(message);
    }

    public static void displayListOfMenuCommands() {
        System.out.print("\n");
        System.out.print("\n");
        String[] commands = {
                "calculator", "browser", "notepad",
                "commands", "info", "languages" ,BOLD + RED + "exit" + RESET};
        for (String command : commands) {
            displayContent("· " + command, "white", 58);
        }
        displayMarginBigBorder();
    }

    public static void openSite(String userSite) {
        try {
            URI uri = new URI(userSite);
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
                System.out.println("\r   Opened in browser\n");
            } else {
                displayErrorAscii();
                displayContent("Browsing action not supported. " +
                        "Please manually open the link: " + userSite, "red", 0);
            }
        } catch (URISyntaxException | IOException e) {
            displayMarginBigBorder();
            displayErrorAscii();
            displayContent("Failed to open link: " + e.getMessage(), "red", 0);
        } catch (Exception e) {
            displayErrorAscii();
            displayContent("Unexpected error: " + e.getMessage(), "red", 0);
        }
    }

    public static void displayContent(String text, String colorName, int alignment) {
        ColorFunc.Color color;
        try {
            color = ColorFunc.Color.valueOf(colorName.toUpperCase());
        } catch (IllegalArgumentException e) {
            if (colorName.equalsIgnoreCase("randomly")) {
                color = getRandomColor();
            } else {
                displayErrorAscii();
                displayContent("Invalid input", "red", 0);
                return;
            }
        }

        String coloredText = getColoredText(text, color);
        alignmentLogic(alignment);
        int alignLength = alignment == 0 ? text.length() : alignment;

        if (alignment < 0) {
            displayErrorAscii();
            displayContent("Invalid input", "red", 0);
        } else {
            System.out.println(alignmentLogic(alignLength) + coloredText);
        }
    }

    //In progress
    public static void activityDetection(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                displayMarginBigBorder();
                displayTip("Press any key to unfreeze the app", 58);
                System.out.print(BOLD + WHITE + "Your choice: " + RESET);
                String choice = scanner.nextLine();
                if(choice.equalsIgnoreCase(" ")){}
                displayMarginBigBorder();
            }
        };
        timer.schedule(timerTask, 30000);
    }
}
