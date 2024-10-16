package ui.layout;

import java.util.Random;

import static ui.layout.BasicFunc.displayContent;

public class ThemesFunc {
    private static final String[] COLORS = {
            "red", "blue", "green",
            "yellow", "purple", "cyan",
            "magenta", "white"};

    public static void displayTheme() {
        Random rand = new Random();
        int indexOfLogo = rand.nextInt(11);

        switch (indexOfLogo) {
            case 0:
                logo(COLORS[1], COLORS[3], COLORS[0], COLORS[4], COLORS[1], COLORS[1]);
                break;
            case 1:
                logo(COLORS[7], COLORS[4], COLORS[7], COLORS[7], COLORS[4], COLORS[4]);
                break;
            case 2:
                logo(COLORS[1], COLORS[7], COLORS[1], COLORS[1], COLORS[7], COLORS[7]);
                break;
            case 3:
                logo(COLORS[1], COLORS[7], COLORS[7], COLORS[1], COLORS[1], COLORS[5]);
                break;
            case 4:
                logo(COLORS[7], COLORS[0], COLORS[3], COLORS[1], COLORS[7], COLORS[0]);
                break;
            case 5:
                logo(COLORS[1], COLORS[5], COLORS[5], COLORS[1], COLORS[1], COLORS[5]);
                break;
            case 6:
                logo(COLORS[7], COLORS[4], COLORS[0], COLORS[4], COLORS[7], COLORS[7]);
                break;
            case 7:
                logo(COLORS[5], COLORS[7], COLORS[5], COLORS[5], COLORS[7], COLORS[5]);
                break;
            case 8:
                logo(COLORS[1], COLORS[4], COLORS[7], COLORS[1], COLORS[1], COLORS[4]);
                break;
            case 9:
                logo(COLORS[1], COLORS[1], COLORS[0], COLORS[1], COLORS[7], COLORS[0]);
                break;
            case 10:
                logo(COLORS[5], COLORS[7], COLORS[5], COLORS[6], COLORS[5], COLORS[6]);
                break;
            default:
                logo(COLORS[2], COLORS[2], COLORS[2], COLORS[2], COLORS[2], COLORS[2]);
                break;
        }
    }

    public static void logo(String color1, String color2, String color3,
                            String color4, String color5, String color6) {
        String[] logoLines = {
                "ooo        ooooo             oooo      .    o8o  ",
                "`88.       .888'             `888    .o8    `\"'  ",
                " 888b     d'888  oooo  oooo   888  .o888oo oooo  ",
                " 8 Y88. .P  888  `888  `888   888    888   `888  ",
                " 8  `888'   888   888   888   888    888    888  ",
                " 8    Y     888   888   888   888    888 .  888  ",
                "o8o        o888o  `V88V\"V8P' o888o   \"888\" o888o ",
                "  .oooooo.   ooooo        ooooo       .o.       ",
                " d8P'  `Y8b  `888'        `888'      .888.      ",
                "888           888          888      .8\"888.     ",
                "888           888          888     .8' `888.    ",
                "888           888          888    .88ooo8888.   ",
                "`88b    ooo   888       o  888   .8'     `888.  ",
                " `Y8bood8P'  o888ooooood8 o888o o88o     o8888o "
        };

        String[] colors = {color1, color2, color3, color4, color5, color6};
        for (int i = 0; i < logoLines.length; i++) {
            displayContent(logoLines[i], colors[i % colors.length], 0);
        }
    }

    public static void displayErrorAscii() {
        String[] errorAscii = {
                "  .oooooo.                                 ",
                " d8P'  `Y8b                                ",
                "888      888  .ooooo.  oo.ooooo.   .oooo.o ",
                "888      888 d88' `88b  888' `88b d88(  \"8 ",
                "888      888 888   888  888   888 `\"Y88b.  ",
                "888      888 888   888  888   888 o.  )88b ",
                "`88b    d88' 888   888  888   888 o.  )88b ",
                " `Y8bood8P'  `Y8bod8P'  888bod8P' 8\"\"888P' ",
                "                        888                ",
                "                       o888o               \n"
        };
        for (String line : errorAscii) {
            displayContent(line, "red", 0);
        }
    }
}
