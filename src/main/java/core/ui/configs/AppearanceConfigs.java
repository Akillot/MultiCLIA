package core.ui.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Random;

import static core.ui.configs.TextConfigs.*;
import static java.lang.System.out;

public class AppearanceConfigs {

    private static final String CONFIG_PATH = "config.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static AppearanceConfigData config = new AppearanceConfigData();

    public static int getDefaultDelay() { return config.defaultDelay; }
    public static int getSearchingLineAlignment() { return config.searchingLineAlignment; }
    public static int getDefaultTextAlignment() { return config.defaultTextAlignment; }
    public static int getDefaultLogoAlignment() { return config.defaultLogoAlignment; }

    public static int getMainColor() { return config.mainColor; }
    public static int getLayoutColor() { return config.layoutColor; }
    public static int getAcceptanceColor() { return config.acceptanceColor; }
    public static int getRejectionColor() { return config.rejectionColor; }

    public static String getSearchingArrow(){ return config.searchingArrow; }

    public static final int DEFAULT_BORDER_WIDTH = 62;

    public static void setMainColor(int color) {
        config.mainColor = color;
        saveConfig();
    }

    public static void loadConfig() {
        try (Reader reader = new FileReader(CONFIG_PATH)) {
            config = GSON.fromJson(reader, AppearanceConfigData.class);
        } catch (IOException e) {
            setDefaultValues();
            saveConfig();
        }
    }

    public static void saveConfig() {
        try (Writer writer = new FileWriter(CONFIG_PATH)) {
            GSON.toJson(config, writer);
        } catch (IOException ignored) {}
    }

    private static void setDefaultValues() {
        config.defaultDelay = 0;
        config.searchingLineAlignment = 48;
        config.defaultTextAlignment = 58;
        config.defaultLogoAlignment = 48;

        config.color1 = 219;
        config.color2 = 183;
        config.color3 = 147;
        config.color4 = 218;
        config.color5 = 182;
        config.color6 = 218;

        config.mainColor = 147;
        config.layoutColor = 15;
        config.acceptanceColor = 46;
        config.rejectionColor = 160;

        config.searchingArrow = "> ";
    }

    public static final String RESET = "\033[0m";
    public static final String UNDERLINE = "\033[4m";

    @Contract(pure = true)
    public static @NotNull String getColorText(String text, int color) {
        if (color < 0 || color > 255) {
            throw new IllegalArgumentException("Color code must be between 0 and 255");
        }
        return getColor(color) + text + RESET;
    }

    @Contract(pure = true)
    public static @NotNull String getColor(int colorCode) {
        return "\033[38;5;" + colorCode + "m";
    }

    @Contract(pure = true)
    public static @NotNull String getBackColor(int colorCode) {
        return "\033[48;5;" + colorCode + "m";
    }

    public static @NotNull String getRandomColor() {
        Random rand = new Random();
        int colorCode = rand.nextInt(256);
        return getColor(colorCode);
    }

    public static @NotNull String getRandomBackColor() {
        Random rand = new Random();
        int colorCode = rand.nextInt(256);
        return getBackColor(colorCode);
    }

    public static void border() {
        message("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",
                getLayoutColor(),
                DEFAULT_BORDER_WIDTH,
                0,
                out::print);
    }

    public static void marginBorder(int upperSide, int lowerSide) {
        if (upperSide >= 0 && lowerSide >= 0) {
            insertControlChars('n', upperSide);
            border();
            insertControlChars('n', lowerSide);
        }
        else{
            message("Negative amount",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);
        }
    }

    //Color table components
    @Contract(pure = true)
    public static void displayColorTable() {
        insertControlChars('n',1);
        message("Color Table:",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);
        printColorRange(0, getLayoutColor());
        printColorBlock();
        printColorRange(232, 255);
    }

    @Contract(pure = true)
    private static void printColorRange(int start, int end) {
        out.println(RESET);
        for (int i = start; i <= end; i++) {
            out.print(getColor(getLayoutColor()) + getBackColor(i)
                    + tableAlignment() + " " + i + " " + RESET);
            if ((i - start + 1) % 8 == 0) insertControlChars('n',2);
        }
    }

    @Contract(pure = true)
    private static void printColorBlock() {
        int columns = 6;
        int rows = (231 - 16 + 1) / columns;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int colorCode = 16 + row + col * rows;
                out.print(getColor(getLayoutColor()) + getBackColor(colorCode)
                        + tableAlignment() + " " + colorCode + " " + RESET);
            }
            if(row == 11){
                insertControlChars('n',1);
            }
            insertControlChars('n', 1);
        }
    }



    @Contract(pure = true)
    private static @NotNull String tableAlignment() {
        return " ".repeat(Math.max(0, 4));
    }
}