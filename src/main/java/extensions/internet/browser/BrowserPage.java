package extensions.internet.browser;

import static core.logic.BorderConfigs.border;
import static core.logic.TextConfigs.modifyMessage;
import static extensions.internet.browser.BrowserConfigs.browser;

public class BrowserPage {
    public static void browserPage() {
        modifyMessage('n', 2);
        border();
        browser();
    }
}