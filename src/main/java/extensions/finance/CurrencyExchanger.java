package extensions.finance;

import org.json.JSONObject;

import static core.logic.ApiConfigs.httpRequest;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.terminateExtension;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.alignment;
import static core.logic.TextConfigs.capitalize;
import static java.lang.System.out;

public class CurrencyExchanger {
    public static void exchanger() {
        alert("i", "Enter the name of\n"
                + alignment(58) + "a cryptocurrency, then\n"
                + alignment(58) + "the fiat currency code\n"
                + alignment(58) + "[" + BLUE + BOLD + "Example" + RESET + ": '"
                + BLUE + BOLD + "usd" + RESET + WHITE + BOLD + "']" + RESET, 58);

        messageModifier('n', 1);
        message("━━━━━━━━━━━━━━━━━━━━━━", "white", 58, 0, out::print);
        messageModifier('n', 1);

        alert("i", "Type '" + BLUE + BOLD + "exit" + RESET + BOLD + "' to\n" +
                alignment(58) + "quit the extension.", 58);

        while (true) {
            messageModifier('n', 1);
            out.print(alignment(58) + BLUE + BOLD + "Cryptocurrency" + RESET + WHITE + BOLD + ": ");
            String userCryptocurrency = scanner.nextLine().trim();

            if (userCryptocurrency.equalsIgnoreCase("exit")) {
                messageModifier('n', 1);
                terminateExtension();
                break;
            }

            if (userCryptocurrency.isEmpty()) {
                message("Cryptocurrency name cannot be empty.", "red", 58, 0, out::print);
                continue;
            }

            out.print(alignment(58) + BLUE + BOLD + "Fiat code" + RESET + WHITE + BOLD + ": ");
            String userFiatCurrencyCode = scanner.nextLine().trim();

            if (userFiatCurrencyCode.equalsIgnoreCase("exit")) {
                messageModifier('n', 1);
                terminateExtension();
                break;
            }

            if (userFiatCurrencyCode.isEmpty()) {
                message("Fiat currency code cannot be empty.", "red", 58, 0, out::print);
                continue;
            }

            messageModifier('n', 1);
            getCryptocurrencyPrice(userCryptocurrency, userFiatCurrencyCode);
        }
    }

    private static void getCryptocurrencyPrice(String cryptocurrencyName, String fiatCurrencyCode) {
        cryptocurrencyName = cryptocurrencyName.toLowerCase();
        fiatCurrencyCode = fiatCurrencyCode.toLowerCase();

        String apiUrl = "https://api.coingecko.com/api/v3/simple/price?ids=" + cryptocurrencyName +
                "&vs_currencies=" + fiatCurrencyCode;
        String response = httpRequest(apiUrl, "GET", "");

        if (response != null) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                if (!jsonResponse.has(cryptocurrencyName)) {
                    message("Invalid cryptocurrency: " + capitalize(cryptocurrencyName), "red", 58, 0, out::print);
                    return;
                }
                if (!jsonResponse.getJSONObject(cryptocurrencyName).has(fiatCurrencyCode)) {
                    message("Invalid fiat currency: " + fiatCurrencyCode.toUpperCase(), "red", 58, 0, out::print);
                    return;
                }

                double price = jsonResponse.getJSONObject(cryptocurrencyName).getDouble(fiatCurrencyCode);
                out.print(alignment(58) + BLUE + BOLD + capitalize(cryptocurrencyName) + RESET);
                out.println(WHITE + BOLD + " costs in " + fiatCurrencyCode.toUpperCase() + ": " + RESET + BLUE + price + RESET);

            } catch (Exception e) {
                message("Error parsing JSON response: " + e.getMessage(), "red", 58, 0, out::print);
            }
        }
    }
}