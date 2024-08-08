package ui.layout;

import java.util.HashMap;
import java.util.Random;

import static ui.layout.ColorWork.*;
import static ui.layout.TextWork.contentAlignment;

public class LogoWork {

    public static String nameOfLogo = "default"; //temporary

    public static void logoInitializer(String requiredCommand) {
        HashMap<String, Runnable> listOfCommands = new HashMap<>();

        listOfCommands.put("small-default", LogoWork::displaySmallDefaultLogo);
        listOfCommands.put("small-google", LogoWork::displaySmallGoogleReferenceLogo);
        listOfCommands.put("small-neon", LogoWork::displaySmallNeonDefaultLogo);

        listOfCommands.put("elite", LogoWork::displayBigAsciiLogo1);
        listOfCommands.put("default", LogoWork::displayBigAsciiLogo2);
        listOfCommands.put("smooth", LogoWork::displayBigAsciiLogo3);

        listOfCommands.put("exit", BasicFunctions::exitBlock);

        Runnable command = listOfCommands.get(requiredCommand.toLowerCase());
        if (command != null) {
            command.run();
        } else {
            displayColorCommand("Command not found", "red", (byte) 0);
        }
    }


    //Small default logo
    public static void displaySmallDefaultLogo() {
        System.out.print(contentAlignment(11) +
                BOLD + "+---------+\n" +
                contentAlignment(11) + "|" +
                RED + BOLD + "M" + RESET + GREEN + BOLD + "u" + RESET +
                YELLOW + BOLD + "l" + RESET + BLUE + BOLD + "t" + RESET +
                PURPLE + BOLD + "i" + RESET + BOLD + "CLIA|\n" +
                contentAlignment(11) + "+---------+\n" + RESET);
    }

    //Small google-like logo
    public static void displaySmallGoogleReferenceLogo() {
        System.out.print(contentAlignment(11) + "+---------+\n" +
                contentAlignment(11) + "|" +
                BLUE + "M" + RESET + RED + "u" + RESET + YELLOW + "l" + RESET +
                BLUE + "ti" + RESET + GREEN + "CL" + RESET + RED + "IA" + RESET + "|\n" +
                contentAlignment(11) + "+---------+\n");
    }

    //Small neon logo
    public static void displaySmallNeonDefaultLogo() {
        System.out.print(contentAlignment(11) +
                BOLD + "+---------+\n" +
                contentAlignment(11) + "|" +
                RED + BOLD + "M" + RESET + PURPLE + BOLD + "u" + RESET +
                RED + BOLD + "l" + RESET + PURPLE + BOLD + "t" + RESET +
                RED + BOLD + "i" + RESET + BOLD + "CLIA|\n" +
                contentAlignment(11) + "+---------+\n" + RESET);
    }


    public static void displayRandomLogo() {
        Random random = new Random();
        int randIndexOfLogo = random.nextInt(1, 7);

        switch (randIndexOfLogo) {
            case 1:

        }
    }


    public static void displayBigAsciiLogo1() {
        displayColorCommand("• ▌ ▄ ·. ▄• ▄▌▄▄▌  ▄▄▄▄▄▪   ▄▄· ▄▄▌  ▪   ▄▄▄· ", "randomly", (byte) 0);
        displayColorCommand("·██ ▐███▪█▪██▌██•  •██  ██ ▐█ ▌▪██•  ██ ▐█ ▀█ ", "randomly", (byte) 0);
        displayColorCommand("▐█ ▌▐▌▐█·█▌▐█▌██▪   ▐█.▪▐█·██ ▄▄██▪  ▐█·▄█▀▀█ ", "randomly", (byte) 0);
        displayColorCommand("██ ██▌▐█▌▐█▄█▌▐█▌▐▌ ▐█▌·▐█▌▐███▌▐█▌▐▌▐█▌▐█ ▪▐▌", "randomly", (byte) 0);
        displayColorCommand("▀▀  █▪▀▀▀ ▀▀▀ .▀▀▀  ▀▀▀ ▀▀▀·▀▀▀ .▀▀▀ ▀▀▀ ▀  ▀ ", "randomly", (byte) 0);
    }


    public static void displayBigAsciiLogo2() {
        displayColorCommand(" __  __       _ _   _  _____ _      _____          ", "randomly", (byte) 0);
        displayColorCommand("|  \\/  |     | | | (_)/ ____| |    |_   _|   /\\    ", "randomly", (byte) 0);
        displayColorCommand("| \\  / |_   _| | |_ _| |    | |      | |    /  \\   ", "randomly", (byte) 0);
        displayColorCommand("| |\\/| | | | | | __| | |    | |      | |   / /\\ \\  ", "randomly", (byte) 0);
        displayColorCommand("| |  | | |_| | | |_| | |____| |____ _| |_ / ____ \\ ", "randomly", (byte) 0);
        displayColorCommand("|_|  |_|\\__,_|_|\\__|_|\\_____|______|_____/_/    \\_\\", "randomly", (byte) 0);
    }

    public static void displayBigAsciiLogo3() {
        displayColorCommand(" ______        _      _  ______ _       _____        ", "randomly", (byte) 0);
        displayColorCommand("|  ___ \\      | |_   (_)/ _____) |     (_____)  /\\   ", "randomly", (byte) 0);
        displayColorCommand("| | _ | |_   _| | |_  _| /     | |        _    /  \\  ", "randomly", (byte) 0);
        displayColorCommand("| || || | | | | |  _)| | |     | |       | |  / /\\ \\ ", "randomly", (byte) 0);
        displayColorCommand("| || || | |_| | | |__| | \\_____| |_____ _| |_| |__| |", "randomly", (byte) 0);
        displayColorCommand("|_||_||_|\\____|_|\\___)_|\\______)_______|_____)______|", "randomly", (byte) 0);
    }
}