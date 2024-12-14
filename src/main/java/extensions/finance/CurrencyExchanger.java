package extensions.finance;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static core.logic.ApiConfigs.httpRequest;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.terminateExtension;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.*;

import static java.lang.System.out;

public class CurrencyExchanger {

    private static final Map<String, String> CRYPTO_MAP = new HashMap<>() {{
        put("btc", "bitcoin");
        put("eth", "ethereum");
        put("xrp", "ripple");
        put("ltc", "litecoin");
        put("doge", "dogecoin");
        put("ada", "cardano");
        put("bnb", "binancecoin");
        put("dot", "polkadot");
        put("usdt", "tether");
        put("sol", "solana");
        put("shib", "shiba-inu");
        put("avax", "avalanche-2");
        put("trx", "tron");
        put("sui", "sui");
        put("matic", "polygon");
        put("uni", "uniswap");
        put("link", "chainlink");
        put("atom", "cosmos");
        put("etc", "ethereum-classic");
        put("xlm", "stellar");
        put("fil", "filecoin");
        put("icp", "internet-computer");
        put("algo", "algorand");
        put("qnt", "quant");
        put("apt", "aptos");
        put("hbar", "hedera");
        put("eos", "eos");
        put("neo", "neo");
        put("ftm", "fantom");
        put("sand", "the-sandbox");
        put("mana", "decentraland");
        put("chz", "chiliz");
        put("crv", "curve-dao-token");
        put("aave", "aave");
        put("dydx", "dydx");
        put("lunc", "terra-luna");
        put("bch", "bitcoin-cash");
        put("zec", "zcash");
        put("dash", "dash");
        put("xmr", "monero");
        put("gala", "gala");
        put("ape", "apecoin");
    }};


    public static void exchanger() {
        alert("i", "Type '" + getAnsi256Color(systemDefaultColor) + "exit"
                + getAnsi256Color(systemDefaultWhite) + "' to\n" + alignment(58) + "quit the extension.", 58);

        modifyMessage('n', 1);

        out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + "["
                + getAnsi256Color(systemDefaultColor) + "Example" + getAnsi256Color(systemDefaultWhite) +
                ": '" + getAnsi256Color(systemDefaultColor) + "btc" + getAnsi256Color(systemDefaultWhite) +
                " → " + RESET + getAnsi256Color(systemDefaultColor) + "usd" + getAnsi256Color(systemDefaultWhite) + "']" + RESET);

        modifyMessage('n', 1);

        while (true) {
            modifyMessage('n', 1);
            out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + "Cryptocurrency code: " + RESET);
            String userCryptoCode = scanner.nextLine().trim().toLowerCase();

            if (userCryptoCode.equalsIgnoreCase("exit")) {
                modifyMessage('n', 1);
                terminateExtension();
                break;
            }

            if (userCryptoCode.isEmpty()) {
                message("Cryptocurrency code cannot be empty", systemDefaultRed, 58, 0, out::print);
                continue;
            }

            String cryptocurrencyName = CRYPTO_MAP.getOrDefault(userCryptoCode, userCryptoCode);

            out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + "Fiat currency code: " + RESET);
            String userFiatCurrencyCode = scanner.nextLine().trim().toLowerCase();

            if (userFiatCurrencyCode.equalsIgnoreCase("exit")) {
                modifyMessage('n', 1);
                terminateExtension();
                break;
            }

            if (userFiatCurrencyCode.isEmpty()) {
                message("Fiat currency code cannot be empty.", systemDefaultRed, 58, 0, out::print);
                continue;
            }

            getCryptocurrencyPrice(cryptocurrencyName, userFiatCurrencyCode);
        }
    }

    private static void getCryptocurrencyPrice(String cryptocurrencyName, String fiatCurrencyCode) {
        cryptocurrencyName = cryptocurrencyName.toLowerCase();
        fiatCurrencyCode = fiatCurrencyCode.toLowerCase();

        String response = httpRequest("https://api.coingecko.com/api/v3/simple/price?ids="
                + cryptocurrencyName + "&vs_currencies=" + fiatCurrencyCode, "GET", "", "response");

        if (response != null) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                if (!jsonResponse.has(cryptocurrencyName)) {
                    message("Invalid cryptocurrency: " + capitalizeMessage(cryptocurrencyName), systemDefaultRed, 58, 0, out::print);
                    return;
                }
                if (!jsonResponse.getJSONObject(cryptocurrencyName).has(fiatCurrencyCode)) {
                    message("Invalid fiat currency: " + fiatCurrencyCode.toUpperCase(), systemDefaultRed, 58, 0, out::print);
                    return;
                }

                double price = jsonResponse.getJSONObject(cryptocurrencyName).getDouble(fiatCurrencyCode);
                out.print(alignment(58) + getAnsi256Color(systemDefaultColor) + capitalizeMessage(cryptocurrencyName) + RESET);
                out.println(getAnsi256Color(systemDefaultWhite) + " costs in " + fiatCurrencyCode.toUpperCase() + ": "
                        + RESET + getAnsi256Color(systemDefaultColor) + price + RESET);

            } catch (Exception e) {
                message("Error parsing JSON response: " + e.getMessage(), systemDefaultRed, 58, 0, out::print);
            }
        }
    }
}