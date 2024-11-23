package extensions.browser;
import core.logic.DisplayManager;

import static core.logic.BorderConfigs.border;
import static core.logic.DisplayManager.switchlogoAscii;
import static core.logic.DisplayManager.messageModifier;
import static extensions.browser.BrowserFunc.browser;
import static extensions.browser.BrowserFunc.browserLogo;

public class BrowserPage {
    public static void browserStarter() {
        messageModifier('n', 2);
        DisplayManager.switchlogoAscii(browserLogo,48);
        border();
        browser();
    }
}