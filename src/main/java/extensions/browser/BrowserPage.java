package extensions.browser;
import static core.layout.BorderFunc.bigBorder;
import static core.layout.DisplayManager.logoAscii;
import static core.layout.DisplayManager.messageModifier;
import static extensions.browser.BrowserFunc.browser;
import static extensions.browser.BrowserFunc.browserLogo;

public class BrowserPage {
    public static void browserStarter() {
        messageModifier('n', 2);
        logoAscii(browserLogo,48);
        bigBorder();
        browser();
    }
}