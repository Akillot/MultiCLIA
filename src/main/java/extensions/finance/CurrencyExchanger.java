package extensions.finance;

import org.json.JSONObject;

import static core.logic.ApiConfigs.httpRequest;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.terminateExtension;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.*;

import static java.lang.System.out;

public class CurrencyExchanger {
    public static void exchanger() {
        alert("i", "Type '" + getAnsi256Color(systemDefaultColor) + BOLD + "exit" + RESET + BOLD + "' to\n" +
                alignment(58) + "quit the extension.", 58);

        modifyMessage('n',1);

        out.print(alignment(58) + BOLD + "[" + getAnsi256Color(systemDefaultColor) + BOLD + "Example"
                + RESET + BOLD + ": '" + getAnsi256Color(systemDefaultColor) + BOLD + "bitcoin" + RESET + getAnsi256Color(systemDefaultWhite)
                + BOLD + " → " + RESET + getAnsi256Color(systemDefaultColor) + BOLD + "usd" + RESET + BOLD + "']" + RESET);

        modifyMessage('n', 1);

        while (true) {
            modifyMessage('n', 1);
            out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + BOLD + "Cryptocurrency: " + RESET);
            String userCryptocurrency = scanner.nextLine().trim();

            if (userCryptocurrency.equalsIgnoreCase("exit")) {
                modifyMessage('n', 1);
                terminateExtension();
                break;
            }

            if (userCryptocurrency.isEmpty()) {
                message("Cryptocurrency name cannot be empty",systemDefaultRed,58,0,out::print);
                continue;
            }

            out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + BOLD + "Fiat code: " + RESET);
            String userFiatCurrencyCode = scanner.nextLine().trim();

            if (userFiatCurrencyCode.equalsIgnoreCase("exit")) {
                modifyMessage('n', 1);
                terminateExtension();
                break;
            }

            if (userFiatCurrencyCode.isEmpty()) {
                message("Fiat currency code cannot be empty.",systemDefaultRed,58,0,out::print);
                continue;
            }

            getCryptocurrencyPrice(userCryptocurrency, userFiatCurrencyCode);
        }
    }

    private static void getCryptocurrencyPrice(String cryptocurrencyName, String fiatCurrencyCode) {
        cryptocurrencyName = cryptocurrencyName.toLowerCase();
        fiatCurrencyCode = fiatCurrencyCode.toLowerCase();

        String response = httpRequest("https://api.coingecko.com/api/v3/simple/price?ids="
                + cryptocurrencyName + "&vs_currencies=" + fiatCurrencyCode, "GET", "","response");

        if (response != null) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                if (!jsonResponse.has(cryptocurrencyName)) {
                    message("Invalid cryptocurrency: " + capitalizeMessage(cryptocurrencyName),systemDefaultRed,58,0,out::print);
                    return;
                }
                if (!jsonResponse.getJSONObject(cryptocurrencyName).has(fiatCurrencyCode)) {
                    message("Invalid fiat currency: " + fiatCurrencyCode.toUpperCase(),systemDefaultRed,58,0,out::print);
                    return;
                }

                double price = jsonResponse.getJSONObject(cryptocurrencyName).getDouble(fiatCurrencyCode);
                out.print(alignment(58) + getAnsi256Color(systemDefaultColor) + BOLD + capitalizeMessage(cryptocurrencyName) + RESET);
                out.println(getAnsi256Color(systemDefaultWhite) + BOLD + " costs in " + fiatCurrencyCode.toUpperCase() + ": " + RESET + getAnsi256Color(systemDefaultColor) + price + RESET);

            } catch (Exception e) {
                message("Error parsing JSON response: " + e.getMessage(),systemDefaultRed,58,0,out::print);
            }
        }
    }
}