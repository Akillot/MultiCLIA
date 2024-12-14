package extensions.finance;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static core.logic.ApiConfigs.httpRequest;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.terminateExtension;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.*;

import static java.lang.System.out;

public class CurrencyExchanger {

    private static LinkedList<String> cryptocurrencyCodes = new LinkedList<>() {{
        add("btc");
        add("eth");
        add("xrp");
        add("ltc");
        add("doge");
        add("ada");
        add("bnb");
        add("dot");
        add("usdt");
        add("sol");
        add("shib");
        add("avax");
        add("trx");
        add("sui");
        add("matic");
        add("uni");
        add("link");
        add("atom");
        add("etc");
        add("xlm");
        add("fil");
        add("icp");
        add("algo");
        add("qnt");
        add("apt");
        add("hbar");
        add("eos");
        add("neo");
        add("ftm");
        add("sand");
        add("mana");
        add("chz");
        add("crv");
        add("aave");
        add("dydx");
        add("lunc");
        add("bch");
        add("zec");
        add("dash");
        add("xmr");
        add("gala");
        add("ape");
    }};

    private static LinkedList<String> cryptocurrencyNames = new LinkedList<>() {{
        add("bitcoin");
        add("ethereum");
        add("ripple");
        add("litecoin");
        add("dogecoin");
        add("cardano");
        add("binancecoin");
        add("polkadot");
        add("tether");
        add("solana");
        add("shiba-inu");
        add("avalanche-2");
        add("tron");
        add("sui");
        add("polygon");
        add("uniswap");
        add("chainlink");
        add("cosmos");
        add("ethereum-classic");
        add("stellar");
        add("filecoin");
        add("internet-computer");
        add("algorand");
        add("quant");
        add("aptos");
        add("hedera");
        add("eos");
        add("neo");
        add("fantom");
        add("the-sandbox");
        add("decentraland");
        add("chiliz");
        add("curve-dao-token");
        add("aave");
        add("dydx");
        add("terra-luna");
        add("bitcoin-cash");
        add("zcash");
        add("dash");
        add("monero");
        add("gala");
        add("apecoin");
    }};

    private static final Map<String, String> CRYPTO_MAP = new HashMap<>() {{
        for(int i = 0; i < cryptocurrencyCodes.size() && i < cryptocurrencyNames.size(); i++) {
            put(cryptocurrencyCodes.get(i), cryptocurrencyNames.get(i));
        }
    }};


    public static void exchanger() {
        alert("i", "Type '" + getAnsi256Color(systemDefaultRed) + "exit"
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

            String cryptocurrencyCode = CRYPTO_MAP.getOrDefault(userCryptoCode, userCryptoCode);

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

            getCryptocurrencyPrice(cryptocurrencyCode, userFiatCurrencyCode);
        }
    }

    private static void getCryptocurrencyPrice(String cryptocurrencyCode, String fiatCurrencyCode) {
        cryptocurrencyCode = cryptocurrencyCode.toLowerCase();
        fiatCurrencyCode = fiatCurrencyCode.toLowerCase();

        String response = httpRequest("https://api.coingecko.com/api/v3/simple/price?ids="
                + cryptocurrencyCode + "&vs_currencies=" + fiatCurrencyCode, "GET", "", "response");

        if (response != null) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                if (!jsonResponse.has(cryptocurrencyCode)) {
                    message("Invalid cryptocurrency: " + capitalizeMessage(cryptocurrencyCode), systemDefaultRed, 58, 0, out::print);
                    return;
                }
                if (!jsonResponse.getJSONObject(cryptocurrencyCode).has(fiatCurrencyCode)) {
                    message("Invalid fiat currency: " + fiatCurrencyCode.toUpperCase(), systemDefaultRed, 58, 0, out::print);
                    return;
                }

                double price = jsonResponse.getJSONObject(cryptocurrencyCode).getDouble(fiatCurrencyCode);
                out.print(alignment(58) + getAnsi256Color(systemDefaultColor) + capitalizeMessage(cryptocurrencyCode) + RESET);
                out.println(getAnsi256Color(systemDefaultWhite) + " costs in " + fiatCurrencyCode.toUpperCase() + ": "
                        + RESET + getAnsi256Color(systemDefaultColor) + price + RESET);

            } catch (Exception e) {
                message("Error parsing JSON response: " + e.getMessage(), systemDefaultRed, 58, 0, out::print);
            }
        }
    }
}