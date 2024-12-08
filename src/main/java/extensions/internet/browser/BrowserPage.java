package extensions.internet.browser;

import static core.logic.BorderConfigs.border;
import static core.logic.CommandManager.switchLogo;
import static core.logic.TextConfigs.modifyMessage;
import static extensions.internet.browser.BrowserConfigs.browser;
import static extensions.internet.browser.BrowserConfigs.browserLogo;

public class BrowserPage {
    public static void browserPage() {
        modifyMessage('n', 2);
        switchLogo(browserLogo,48);
        border();
        browser();
    }
}