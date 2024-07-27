package ui.layout;

import java.util.Scanner;

import static ui.layout.BorderWork.drawHorizontalBorder;
import static ui.layout.BorderWork.numberOfSymbols;
import static ui.layout.ColorWork.*;
import static ui.layout.TextWork.contentAlignment;

public class AdditionalFunctions {

    public static Scanner scanner = new Scanner(System.in);

    private static int delay;

    //Show tip for the user
    public static void displayTip(String text) {
        System.out.println(contentAlignment(18) + BOLD
                + "[" + YELLOW + BOLD + "i" + RESET
                + BOLD + "] " + text + RESET);
        drawHorizontalBorder(numberOfSymbols);
    }

    //Exit block(apps)
    public static void exitBlock() {
        drawHorizontalBorder(numberOfSymbols);
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
        drawHorizontalBorder(numberOfSymbols);
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

    //Show list of commands in menu
    public static void displayListOfMenuCommands() {
        System.out.println(contentAlignment(18) + "Commands"
                + contentAlignment(18) + "· calculator\n"
                + contentAlignment(18) + "· settings\n"
                + contentAlignment(18) + "· commands\n"
                + contentAlignment(18) + "· info\n\n"
                + contentAlignment(18) + "· exit");
        drawHorizontalBorder(numberOfSymbols);
    }

    //Show list of commands in settings
    public static void displayListOfSetup() {
        System.out.println("\n" + contentAlignment(58) + "· settings value\n"
                + contentAlignment(58) + "· logo\n"
                + contentAlignment(58) + "· border\n"
                + contentAlignment(58) + "· delay\n"
                // + contentAlignment(18) + "· color-mode\n\n"
                + contentAlignment(58) + "· info\n" //IN PROGRESS
                + contentAlignment(58) + "· exit");
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
        drawHorizontalBorder(numberOfSymbols);
    }
}