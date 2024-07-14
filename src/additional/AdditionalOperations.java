package additional;

import settings.AppearanceSettings;

public class AdditionalOperations {

    // Show list of operations
    public static void showCommandList() {
        System.out.println(AppearanceSettings.border + "\nOperations:\n1. sum[+]\n2. sub[-]" +
                "\n3. multi[*]\n4. div[/]\n5. pow[^]\n\n6. settings\n7. exit[x]\n" + AppearanceSettings.border);
    }
}