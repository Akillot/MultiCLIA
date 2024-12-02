package extensions.finance;

import static core.logic.DisplayManager.messageModifier;
import static extensions.finance.CurrencyExchanger.exchanger;

public class CurrencyExchangerPage {
    public static void exchangerPage() {
        messageModifier('n', 2);
        exchanger();
    }
}
