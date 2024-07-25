package ui.pages;

import static ui.layout.Stylization.contentAlignment;
import static ui.layout.Stylization.displayColorCommand;

public class InfoPage {
    public static void displayInfo() {
        //IN PROGRESS
        displayVersion();
    }

    //Show version
    public static void displayVersion() {
        System.out.println(contentAlignment(18) + "Current version:");
        displayColorCommand("0.4.8", "randomly", (byte) 0);
    }
}
