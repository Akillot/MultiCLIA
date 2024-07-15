package layout;

import settings.AppearanceSettings;

import java.util.Scanner;

public class LayoutSettings {
    public static int width;
    public static String[] symbolsPack;
    static Scanner scanner = new Scanner(System.in);

    //Just to show a logo
    public static void showLogo() {
        System.out.print("    +---------+" + "\n    |" + AppearanceSettings.RED + "M" + AppearanceSettings.RESET +
                AppearanceSettings.GREEN + "u" + AppearanceSettings.RESET +
                AppearanceSettings.YELLOW + "l" + AppearanceSettings.RESET +
                AppearanceSettings.BLUE + "t" + AppearanceSettings.RESET +
                AppearanceSettings.PURPLE + "i" + AppearanceSettings.RESET + "CLIA|\n" + "    +---------+");
    }

    public static Character borderAngle = '+';
    public static String border = "-----------------";

    public static String fullBorder = borderAngle + border + borderAngle;
}
