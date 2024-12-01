package core.pages;

import static core.logic.ApiConfigs.getCryptoCurrencyPrice;
import static core.logic.BorderConfigs.marginBorder;
import static core.logic.DisplayManager.*;

public class TimePage {
    public static void displayCurrentTime() {
        messageModifier('n', 2);
        displayTime();
        getCryptoCurrencyPrice("solana", "eur");
        messageModifier('n', 1);
        marginBorder();
    }
}
