
public class AdditionalOperations {
    // Show list of operations
    static void commandList() {
        System.out.println(AppearanceFeatures.border + "\nOperations:\n1. sum[+]\n2. sub[-]" +
                "\n3. multi[*]\n4. div[/]\n5. pow[^]\n6. info[i]\n7. exit[x]\n" + AppearanceFeatures.border);
    }

    // Show version
    static void versionInfo() {
        System.out.println("Current version:\n0.2.5");
    }
}