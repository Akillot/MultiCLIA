package extensions.browser;
import static core.logic.BorderFunc.border;
import static core.logic.DisplayManager.logoAscii;
import static core.logic.DisplayManager.messageModifier;
import static extensions.browser.BrowserFunc.browser;
import static extensions.browser.BrowserFunc.browserLogo;

public class BrowserPage {
    public static void browserStarter() {
        messageModifier('n', 2);
        logoAscii(browserLogo,48);
        border();
        browser();
    }
}