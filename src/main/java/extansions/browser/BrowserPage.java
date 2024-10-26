package extansions.browser;
import static extansions.browser.BrowserFunc.browser;
import static extansions.browser.BrowserFunc.browserLogo;
import static ui.layout.BorderFunc.displayBigBorder;
import static ui.layout.DisplayManager.logoAscii;

public class BrowserPage {
    public static void browserStarter() {
        System.out.println("\n");
        logoAscii(browserLogo, 58);
        displayBigBorder();
        browser();
    }
}