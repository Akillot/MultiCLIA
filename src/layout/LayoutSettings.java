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

    public static void borderSetup() {
        System.out.println("\nChoose size of border\n1.Small\n2.Middle\n3.Own size\nYour choice is: ");
        String choice = scanner.nextLine().toLowerCase();

        switch (choice) {
            case "small":
            case "1":
                width = 23;
                createHorizontalBorder(width);
                break;
            case "middle":
            case "2":
                width = 27;
                createHorizontalBorder(width);
                break;
            case "own size":
            case "3":
                width = scanner.nextInt();
                createHorizontalBorder(width);
                break;

        }
    }

    public static void createHorizontalBorder(int width) {
        symbolsPack = new String[]{"|", "+", "-"};
        System.out.print(symbolsPack[1]);
        for (int i = 0; i < width; i++) {
            System.out.print(symbolsPack[2]);
        }
        System.out.print(symbolsPack[1]);
    }
}
