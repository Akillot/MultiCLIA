package settings;

import layout.LayoutSettings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BasicSettings {
    // Show version
    public static void showVersionInfo() {
        System.out.println("Current version:\n" + AppearanceSettings.YELLOW + "0.3.2" +
                AppearanceSettings.RESET + "\n" + LayoutSettings.fullBorder);
    }

    public static void showCurrentTime() {
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("\ndd-MM-yyyy \nHH:mm");
        String formattedTime = localTime.format(myFormatter);
        System.out.println(LayoutSettings.fullBorder + "\nTime is: " + formattedTime + LayoutSettings.fullBorder);
    }
}
