package extensions.finance;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static core.logic.AppearanceConfigs.border;
import static core.logic.AppearanceConfigs.getAnsi256Color;
import static core.logic.CommandManager.httpRequest;
import static core.logic.TextConfigs.*;
import static core.logic.TextConfigs.modifyMessage;
import static extensions.finance.CryptoConfigs.*;
import static java.lang.System.out;

public class CryptoService {

    //Getting an actual cryptocurrency prices
    static void getCryptocurrencyPrice(String cryptocurrencyCode, String fiatCurrencyCode) {
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = localTime.format(myFormatter);

        String response = httpRequest(
                "https://api.coingecko.com/api/v3/simple/price?ids="
                        + cryptocurrencyCode + "&vs_currencies=" + fiatCurrencyCode,
                "GET", "", "response");

        if (response != null) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                if (jsonResponse.has(cryptocurrencyCode)) {
                    double price = jsonResponse.getJSONObject(cryptocurrencyCode).getDouble(fiatCurrencyCode);
                    out.println(alignment(58) + getAnsi256Color(themeColor_1)
                            + capitalizeMessage(cryptocurrencyCode) + getAnsi256Color(layoutColor) + " costs in "
                            + fiatCurrencyCode.toUpperCase() + ": "
                            + getAnsi256Color(themeColor_1) + price + getAnsi256Color(layoutColor)
                            + " [" + formattedTime + "]");
                } else {
                    errorFormatting("Invalid response from API.");
                }
            } catch (Exception e) {
                errorFormatting("Error parsing response: " + e.getMessage() + ".");
            }
        } else {
            errorFormatting("Failed to fetch price. Check your network connection.");
        }
    }

    public static void terminateExtension(int themeColor_1, int acceptanceColor, int layoutColor) {
        message("\r   Status: " + getAnsi256Color(acceptanceColor) + "✓", layoutColor,58,0,out::print);
        message("Terminated correctly", themeColor_1,
                58,0,out::print);
        modifyMessage('n',2);
        border();
    }

    //Formating the error outputs
    static void errorFormatting(String text) {
        modifyMessage('n', 1);
        message(text, rejectionColor, 58, 0, out::print);
        modifyMessage('n', 1);
    }
}