
public class AdditionalOperations {
    // Show list of operations
    static void commandList() {
        System.out.println(AppearanceFeatures.border + "\nOperations:\n1. sum[+]\n2. sub[-]" +
                "\n3. multi[*]\n4. div[/]\n5. pow[^]\n6." +
                AppearanceFeatures.RED + " m" + AppearanceFeatures.RESET + AppearanceFeatures.GREEN + "a" +
                AppearanceFeatures.RESET + AppearanceFeatures.YELLOW + "g" + AppearanceFeatures.RESET +
                AppearanceFeatures.BLUE + "i" + AppearanceFeatures.RESET + AppearanceFeatures.PURPLE + "c" +
                AppearanceFeatures.RESET + "[_]" + "\n7. info[i]\n8. exit[x]\n" + AppearanceFeatures.border);
    }

    // Show version
    static void versionInfo() {
        System.out.println("Current version:\n" + AppearanceFeatures.PURPLE + "0.2.6" +
                AppearanceFeatures.RESET + "\n" + AppearanceFeatures.border);
    }
}