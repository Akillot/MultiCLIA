package extensions.time.clock;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.TextConfigs.modifyMessage;
import static extensions.time.clock.Clock.displayTime;

public class ClockPage {
    public static void clockPage() {
        modifyMessage('n', 2);
        displayTime();
        modifyMessage('n', 1);
        marginBorder();
    }
}
