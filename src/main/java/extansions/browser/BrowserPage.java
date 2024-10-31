package extansions.browser;
import static core.layout.BorderFunc.bigBorder;
import static extansions.browser.BrowserFunc.browser;
import static extansions.browser.BrowserFunc.browserLogo;
import static core.layout.DisplayManager.logoAscii;

public class BrowserPage {
    public static void browserStarter() {
        System.out.println("\n");
        logoAscii(browserLogo, 58);
        bigBorder();
        browser();
    }
}