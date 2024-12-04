package extensions.finance;

import static core.logic.TextConfigs.messageModifier;
import static extensions.finance.CurrencyExchanger.exchanger;

public class CurrencyExchangerPage {
    public static void exchangerPage() {
        messageModifier('n', 2);
        exchanger();
    }
}
