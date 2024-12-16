package extensions.finance;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import static core.logic.ApiConfigs.httpRequest;
import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.choice;
import static core.logic.CommandManager.terminateExtension;
import static core.logic.TextConfigs.*;

import static java.lang.System.out;

public class CurrencyExchanger {

    private static final Scanner scanner = new Scanner(System.in);
    private static String cryptocurrencyCode;
    private static String fiatCurrencyCode;

    private static final LinkedList<String> cryptocurrencyCodes = new LinkedList<>() {{
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

    private static final LinkedList<String> cryptocurrencyName = new LinkedList<>() {{
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
        for (int i = 0; i < cryptocurrencyCodes.size() && i < cryptocurrencyName.size(); i++) {
            put(cryptocurrencyCodes.get(i), cryptocurrencyName.get(i));
        }
    }};

    public static void cryptoMenu() {
        modifyMessage('n', 1);
        choice("Exchanger", CurrencyExchanger::exchanger);
        modifyMessage('n', 1);
        choice("Price tracker", CurrencyExchanger::currencyPriceTracker);
        marginBorder();
    }

    //Exchanger method
    private static void exchanger() {
        modifyMessage('n', 2);
        alert("i", "Type '" + getAnsi256Color(systemDefaultRed) + "exit"
                + getAnsi256Color(systemDefaultWhite) + "' to\n" + alignment(58) + "quit the extension.", 58);

        while (true) {
            modifyMessage('n', 1);
            out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + "Cryptocurrency code: " + RESET);
            cryptocurrencyCode = scanner.nextLine().trim().toLowerCase();

            if (cryptocurrencyCode.equalsIgnoreCase("exit")) {
                modifyMessage('n', 1);
                terminateExtension();
                return;
            }

            if (!CRYPTO_MAP.containsKey(cryptocurrencyCode)) {
                message("Invalid cryptocurrency code: " + cryptocurrencyCode, systemDefaultRed, 58, 0, out::print);
                continue;
            }

            out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + "Fiat currency code: " + RESET);
            fiatCurrencyCode = scanner.nextLine().trim().toLowerCase();

            if (fiatCurrencyCode.equalsIgnoreCase("exit")) {
                terminateExtension();
                return;
            }

            if (fiatCurrencyCode.isEmpty()) {
                message("Fiat currency code cannot be empty.", systemDefaultRed, 58, 0, out::print);
                continue;
            }

            getCryptocurrencyPrice(CRYPTO_MAP.get(cryptocurrencyCode), fiatCurrencyCode);
        }
    }

    private static void currencyPriceTracker() {
        modifyMessage('n', 2);
        alert("i", "Type '" + getAnsi256Color(systemDefaultRed) + "exit"
                + getAnsi256Color(systemDefaultWhite) + "' to quit the extension at any time.", 58);
        while (true) {
            modifyMessage('n', 1);
            out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + "Cryptocurrency code: " + RESET);
            cryptocurrencyCode = scanner.nextLine().trim().toLowerCase();

            if (cryptocurrencyCode.equalsIgnoreCase("exit")) {
                terminateExtension();
                return;
            }

            if (!CRYPTO_MAP.containsKey(cryptocurrencyCode)) {
                message("Invalid cryptocurrency code: " + cryptocurrencyCode, systemDefaultRed, 58, 0, out::print);
                continue;
            }

            out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + "Fiat currency code: " + RESET);
            fiatCurrencyCode = scanner.nextLine().trim().toLowerCase();

            if (fiatCurrencyCode.equalsIgnoreCase("exit")) {
                terminateExtension();
                return;
            }

            if (fiatCurrencyCode.isEmpty()) {
                message("Fiat currency code cannot be empty.", systemDefaultRed, 58, 0, out::print);
                continue;
            }

            out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + "Duration in minutes: " + RESET);

            double duration;
            try {
                duration = Integer.parseInt(scanner.nextLine().trim()) * 60000;
            } catch (NumberFormatException e) {
                modifyMessage('n',1);
                message("Invalid duration. Please enter a valid number.", systemDefaultRed, 58, 0, out::print);
                continue;
            }

            if (duration <= 0) {
                modifyMessage('n',1);
                message("Duration must be greater than zero.", systemDefaultRed, 58, 0, out::print);
                continue;
            }

            for (int i = 0; i < duration; i += 5000) {
                getCryptocurrencyPrice(CRYPTO_MAP.get(cryptocurrencyCode), fiatCurrencyCode);
                try {
                    Thread.sleep(50000); //pause 50 sec
                } catch (InterruptedException e) {
                    modifyMessage('n',1);
                    message("Tracking interrupted.", systemDefaultRed, 58, 0, out::print);
                    return;
                }
            }
        }
    }

    private static void getCryptocurrencyPrice(String cryptocurrencyCode, String fiatCurrencyCode) {
        String response = httpRequest(
                "https://api.coingecko.com/api/v3/simple/price?ids=" + cryptocurrencyCode +
                        "&vs_currencies=" + fiatCurrencyCode,
                "GET", "", "response");

        if (response != null) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                if (jsonResponse.has(cryptocurrencyCode)) {
                    double price = jsonResponse.getJSONObject(cryptocurrencyCode).getDouble(fiatCurrencyCode);
                    out.println(alignment(58) + getAnsi256Color(systemDefaultColor)
                            + capitalizeMessage(cryptocurrencyCode) + getAnsi256Color(systemDefaultWhite) + " costs in "
                            + fiatCurrencyCode.toUpperCase() + ": "
                            + getAnsi256Color(systemDefaultColor) + price + RESET);
                } else {
                    modifyMessage('n',1);
                    message("Invalid response from API.", systemDefaultRed, 58, 0, out::print);
                    modifyMessage('n',1);
                }
            } catch (Exception e) {
                modifyMessage('n',1);
                message("Error parsing response: " + e.getMessage(), systemDefaultRed, 58, 0, out::print);
                modifyMessage('n',1);
            }
        } else {
            modifyMessage('n',1);
            message("Failed to fetch price. Check your network connection.", systemDefaultRed, 58, 0, out::print);
            modifyMessage('n',1);
        }
    }
}
