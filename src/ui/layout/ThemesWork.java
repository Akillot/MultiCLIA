package ui.layout;

import java.util.Random;

import static ui.layout.ColorWork.*;

public class ThemesWork {

    public static void displayLogo() {
        Random rand = new Random();
        int indexOfLogo = rand.nextInt(0,7);
        String[] color = new String[]{"red", "blue", "green", "yellow", "purple", "cyan", "magenta", "white" };

        switch (indexOfLogo) {
            case 0:
                logo(color[1],color[3],color[0],color[4],color[1],color[1]);
                break;
            case 1:
                logo(color[7],color[4],color[7],color[7],color[4],color[4]);
                break;
            case 2:
                logo(color[1],color[7],color[1],color[1],color[7],color[7]);
                break;
            case 3:
                logo(color[0],color[7],color[7],color[3],color[3],color[0]);
                break;
            case 4:
                logo(color[7],color[0],color[3],color[1],color[7],color[0]);
                break;
            case 5:
                logo(color[1],color[5],color[5],color[1],color[1],color[5]);
                break;
            case 6:
                logo(color[7],color[4],color[0],color[4],color[7],color[7]);
                break;

        }
    }
    public static void logo(String color1, String color2, String color3, String color4, String color5, String color6) {
        displayColorCommand("      __   __       _ _   _  _____ _      _____                 ", color1, (byte) 0);
        displayColorCommand("      |  \\/  |     | | | (_)/ ____| |    |_   _|   /\\           ", color2, (byte) 0);
        displayColorCommand("      | \\  / |_   _| | |_ _| |    | |      | |    /  \\          ", color3, (byte) 0);
        displayColorCommand("      | |\\/| | | | | | __| | |    | |      | |   / /\\ \\         ", color4, (byte) 0);
        displayColorCommand("      | |  | | |_| | | |_| | |____| |____ _| |_ / ____ \\        ", color5, (byte) 0);
        displayColorCommand("      |_|  |_|\\__,_|_|\\__|_|\\_____|______|_____/_/    \\_\\       ", color6, (byte) 0);
    }
}