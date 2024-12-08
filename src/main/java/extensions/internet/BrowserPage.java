package extensions.internet;

import static core.logic.BorderConfigs.border;
import static core.logic.CommandManager.switchLogo;
import static core.logic.TextConfigs.modifyMessage;
import static extensions.internet.BrowserConfigs.browser;
import static extensions.internet.BrowserConfigs.browserLogo;

public class BrowserPage {
    public static void browserPage() {
        modifyMessage('n', 2);
        switchLogo(browserLogo,48);
        border();
        browser();
    }
}