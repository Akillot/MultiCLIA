package extensions.browser;
import static core.layout.BorderFunc.bigBorder;
import static extensions.browser.BrowserFunc.browser;
import static extensions.browser.BrowserFunc.browserLogo;
import static core.layout.DisplayManager.logoAscii;
import static java.lang.System.out;

public class BrowserPage {
    public static void browserStarter() {
        out.println("\n");
        logoAscii(browserLogo, 58);
        bigBorder();
        browser();
    }
}