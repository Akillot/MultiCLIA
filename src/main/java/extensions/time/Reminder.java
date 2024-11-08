package extensions.time;

import static core.logic.ColorFunc.*;
import static core.logic.DisplayManager.messageModifier;
import static core.logic.TextFunc.alignment;
import static java.lang.System.out;

public class Reminder {
    public static void reminderStarted(){
        messageModifier('n',2);
        out.print(alignment(58) + WHITE + BOLD + "Set date and time to remind: " + RESET);

    }
}
