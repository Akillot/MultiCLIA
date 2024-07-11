package additional;

import appearance.AppearanceFeatures;

public class AdditionalOperations {
    // Show list of operations
    public static void commandList() {
        System.out.println(AppearanceFeatures.border + "\nOperations:\n1. sum[+]\n2. sub[-]" +
                "\n3. multi[*]\n4. div[/]\n5. pow[^]\n6. magic[_]" + "\n7. info[i]\n8. exit[x]\n9. layout\n" + AppearanceFeatures.border);
    }

    // Show version
    public static void versionInfo() {
        System.out.println("Current version:\n" + AppearanceFeatures.PURPLE + "0.2.7" +
                AppearanceFeatures.RESET + "\n" + AppearanceFeatures.border);
    }
}