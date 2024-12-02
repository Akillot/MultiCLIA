package extensions.internet.browser;

import static core.logic.BorderConfigs.border;
import static core.logic.DisplayManager.messageModifier;
import static core.logic.DisplayManager.switchLogoAscii;
import static extensions.internet.browser.BrowserConfigs.browser;
import static extensions.internet.browser.BrowserConfigs.browserLogo;

public class BrowserPage {
    public static void browserPage() {
        messageModifier('n', 2);
        switchLogoAscii(browserLogo,48);
        border();
        browser();
    }
}