package extensions.finance;

import static core.logic.TextConfigs.modifyMessage;
import static extensions.finance.CurrencyExchanger.exchanger;

public class CurrencyExchangerPage {
    public static void exchangerPage() {
        modifyMessage('n', 2);
        exchanger();
    }
}
