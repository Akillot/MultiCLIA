package ui.layout;

import java.util.Scanner;

import static ui.layout.Stylization.*;

public class UiLayout {

    public static Scanner scanner = new Scanner(System.in);

    public static String nameOfLogo = "";
    private static int delay;


    //VERSION//INFO//TIP//
    //Show info about MultiCLIA

    //Show tip for the user
    public static void displayTip(String text) {
        System.out.println(contentAlignment(18) + BOLD
                + "[" + YELLOW + BOLD + "i" + RESET
                + BOLD + "] " + text + RESET);
        drawFullTripleBorder();
    }


    //EXIT//
    //Exit block(apps)
    public static void exitBlock() {
        drawFullTripleBorder();
        delay = 150;
        String exitText = contentAlignment(18) + RED + BOLD + "Application exit" + RESET;
        for (char ch : exitText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorCommand("Error, try again", "red", (byte) 0);
            }
        }
        delay = 250;
        String exitTextAdditional = RED + "...\n" + RESET;
        for (char ch : exitTextAdditional.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorCommand("Error, try again", "red", (byte) 0);
            }
        }
        drawFullTripleBorder();
    }

    //Exit program
    public static void exitProgram() {
        delay = 150;
        String exitText = contentAlignment(18) + RED + "Program exit" + RESET;
        for (char ch : exitText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorCommand("Error, try again", "red", (byte) 0);
            }
        }
        delay = 250;
        String exitTextAdditional = RED + "..." + RESET;
        for (char ch : exitTextAdditional.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorCommand("Error, try again", "red", (byte) 0);
            }
        }
        System.exit(0);
    }


    //LISTS OF COMMANDS//
    //Show list of commands in menu
    public static void displayListOfMenuCommands() {
        System.out.println(contentAlignment(8) + "Commands\n"
                + contentAlignment(18) + "· calculator\n"
                + contentAlignment(18) + "· settings\n"
                + contentAlignment(18) + "· commands\n"
                + contentAlignment(18) + "· info\n\n"
                + contentAlignment(18) + "· exit");
        drawFullTripleBorder();
    }

    //Show list of commands in settings
    public static void displayListOfSettings() {
        System.out.println(contentAlignment(8) + "Commands\n"
                + contentAlignment(18) + "· settings value\n"
                + contentAlignment(18) + "· logo\n"
                + contentAlignment(18) + "· border\n"
                + contentAlignment(18) + "· delay\n"
                + contentAlignment(18) + "· color-mode\n\n"
                + contentAlignment(18) + "· info\n" //IN PROGRESS
                + contentAlignment(18) + "· exit");
        drawFullTripleBorder();
    }

    //IN PROGRESS
    //Show list of color modes
    public static void displayListOfColorModes() {
        System.out.println(contentAlignment(5) + "Modes\n"
                + contentAlignment(18) + "· high contrast\n"
                + contentAlignment(18) + "· mono color\n"
                + contentAlignment(18) + "· no color\n\n"
                + contentAlignment(18) + "· info\n"
                + contentAlignment(18) + "· exit");
        drawFullTripleBorder();
    }
}