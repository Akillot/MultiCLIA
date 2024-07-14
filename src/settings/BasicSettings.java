package settings;

public class BasicSettings {
    // Show version
    public static void versionInfo() {
        System.out.println("Current version:\n" + AppearanceSettings.YELLOW + "0.3" +
                AppearanceSettings.RESET + "\n" + AppearanceSettings.border);
    }
}
