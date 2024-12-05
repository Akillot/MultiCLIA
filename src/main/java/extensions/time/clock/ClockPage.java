package extensions.time.clock;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.TextConfigs.messageModifier;
import static extensions.time.clock.Clock.displayTime;

public class ClockPage {
    public static void clockPage() {
        messageModifier('n', 2);
        displayTime();
        messageModifier('n', 1);
        marginBorder();
    }
}
