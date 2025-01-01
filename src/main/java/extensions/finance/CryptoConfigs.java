package extensions.finance;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static core.logic.ApiConfigs.httpRequest;
import static core.logic.AppearanceConfigs.*;
import static core.logic.CommandManager.*;
import static core.logic.TextConfigs.*;

import static java.lang.System.out;

//Not a final version
public class CryptoConfigs {

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

    public static String[] cryptoLogo = {
            "  .oooooo.                                       .             ",
            " d8P'  `Y8b                                    .o8             ",
            "888          oooo d8b oooo    ooo oo.ooooo.  .o888oo  .ooooo.  ",
            "888          `888\"\"8P  `88.  .8'   888' `88b   888   d88' `88b ",
            "888           888       `88..8'    888   888   888   888   888 ",
            "`88b    ooo   888        `888'     888   888   888 . 888   888 ",
            " `Y8bood8P'  d888b        .8'      888bod8P'   \"888\" `Y8bod8P' ",
            "                      .o..P'       888                         ",
            "                      `Y8P'       o888o                        ",
            " "
    };

    private static final Scanner scanner = new Scanner(System.in);
    private static String cryptocurrencyCode;
    private static String fiatCurrencyCode;

    private static int themeColor_1 = 85;
    private static int themeColor_2 = 79;
    private static int layoutColor = 15;

    private static int acceptanceColor = 46;
    private static int rejectionColor = 196;

    private static final Map<String, String> CRYPTO_MAP = new HashMap<>() {{
        for (int i = 0; i < cryptocurrencyCodes.size() && i < cryptocurrencyName.size(); i++) {
            put(cryptocurrencyCodes.get(i), cryptocurrencyName.get(i));
        }
    }};

    //Main menu of extension
    public static void displayCryptoMenu() {
        modifyMessage('n', 2);
        switchLogoRandomly(cryptoLogo,8);
        marginBorder(1,1);
        modifyMessage('n', 1);

        displayConfirmation("Enter","to open and","to skip", themeColor_1, rejectionColor, layoutColor);
        choice("List of cryptocurrencies", CryptoConfigs::displayListOfCryptocurrencies,
                themeColor_1, layoutColor, rejectionColor);

        displayConfirmation("Enter","to open and","to skip", themeColor_1, rejectionColor, layoutColor);
        choice("Exchanger", CryptoConfigs::exchanger,
                themeColor_1, layoutColor, rejectionColor);

        displayConfirmation("Enter","to open and","to skip", themeColor_1, rejectionColor, layoutColor);
        choice("Price tracker", CryptoConfigs::currencyPriceTracker,
                themeColor_1, layoutColor, rejectionColor);
    }

    //Exchanger method
    private static void exchanger() {
        modifyMessage('n', 2);
        alert("i", "Type '" + getAnsi256Color(themeColor_1) + "exit"
                + getAnsi256Color(layoutColor) + "' to\n" + alignment(58) + "quit the extension.",
                58, themeColor_1, layoutColor);

        while (true) {
            modifyMessage('n', 1);
            out.print(alignment(58) + getAnsi256Color(layoutColor) + "Cryptocurrency code: " + RESET);
            cryptocurrencyCode = scanner.nextLine().trim().toLowerCase();

            if (cryptocurrencyCode.equalsIgnoreCase("exit")) {
                modifyMessage('n', 1);
                terminateExtension();
                return;
            }

            if (!CRYPTO_MAP.containsKey(cryptocurrencyCode)) {
                message("Invalid cryptocurrency code: " + cryptocurrencyCode, rejectionColor, 58, 0, out::print);
                continue;
            }

            out.print(alignment(58) + getAnsi256Color(layoutColor) + "Fiat currency code: " + RESET);
            fiatCurrencyCode = scanner.nextLine().trim().toLowerCase();

            if (fiatCurrencyCode.equalsIgnoreCase("exit")) {
                terminateExtension();
                return;
            }

            if (fiatCurrencyCode.isEmpty()) {
                message("Fiat currency code cannot be empty.", rejectionColor, 58, 0, out::print);
                continue;
            }

            getCryptocurrencyPrice(CRYPTO_MAP.get(cryptocurrencyCode), fiatCurrencyCode);
        }
    }

    //Tracker
    private static void currencyPriceTracker() {
        modifyMessage('n', 2);
        alert("i", "Type '" + getAnsi256Color(rejectionColor) + "exit"
                + getAnsi256Color(layoutColor) + "' to quit the extension at any time.\n"
                + alignment(58) + "You cannot exit this mode while tracking is in progress.", 58,
                themeColor_1, layoutColor);
        while (true) {
            modifyMessage('n', 1);
            out.print(alignment(58) + getAnsi256Color(layoutColor) + "Cryptocurrency code: " + RESET);
            cryptocurrencyCode = scanner.nextLine().trim().toLowerCase();

            if (cryptocurrencyCode.equalsIgnoreCase("exit")) {
                terminateExtension();
                return;
            }

            if (!CRYPTO_MAP.containsKey(cryptocurrencyCode)) {
                message("Invalid cryptocurrency code: " + cryptocurrencyCode, rejectionColor, 58, 0, out::print);
                continue;
            }

            out.print(alignment(58) + getAnsi256Color(layoutColor) + "Fiat currency code: " + RESET);
            fiatCurrencyCode = scanner.nextLine().trim().toLowerCase();

            if (fiatCurrencyCode.equalsIgnoreCase("exit")) {
                terminateExtension();
                return;
            }

            if (fiatCurrencyCode.isEmpty()) {
                message("Fiat currency code cannot be empty.", rejectionColor, 58, 0, out::print);
                continue;
            }

            out.print(alignment(58) + getAnsi256Color(layoutColor) + "Duration in minutes: " + RESET);

            double duration;
            try {
                duration = Integer.parseInt(scanner.nextLine().trim()) * 60000;
            } catch (NumberFormatException e) {
                modifyMessage('n', 1);
                message("Invalid duration. Please enter a valid number.", rejectionColor, 58, 0, out::print);
                continue;
            }

            if (duration <= 0) {
                modifyMessage('n', 1);
                message("Duration must be greater than zero.", rejectionColor, 58, 0, out::print);
                continue;
            }

            for (int i = 0; i < duration; i += 5000) {
                getCryptocurrencyPrice(CRYPTO_MAP.get(cryptocurrencyCode), fiatCurrencyCode);
                try {
                    Thread.sleep(40000); //pause 40 sec
                } catch (InterruptedException e) {
                    errorFormatting("Tracking interrupted.");
                    return;
                }
            }
        }
    }

    //Lists of cryptocurrencies
    private static void displayListOfCryptocurrencies() {
        modifyMessage('n', 2);
        try {
            if (cryptocurrencyName.size() != cryptocurrencyCodes.size()) {
                throw new IllegalStateException("Cryptocurrency lists are not synchronized.");
            }

            int totalItems = cryptocurrencyName.size();
            int midPoint = (totalItems + 1) / 2;

            List<String> leftColumnNames = cryptocurrencyName.subList(0, midPoint);
            List<String> leftColumnCodes = cryptocurrencyCodes.subList(0, midPoint);
            List<String> rightColumnNames = cryptocurrencyName.subList(midPoint, totalItems);
            List<String> rightColumnCodes = cryptocurrencyCodes.subList(midPoint, totalItems);

            int maxRows = Math.max(leftColumnNames.size(), rightColumnNames.size());

            out.printf(alignment(38) + getAnsi256Color(layoutColor) + "%-40s"
                    + alignment(10) + getAnsi256Color(layoutColor) + "%-40s%n",
                    getAnsi256Color(themeColor_1) + "Names",getAnsi256Color(themeColor_1) + "          Codes");

            for (int i = 0; i < maxRows; i++) {
                String leftEntry = i < leftColumnNames.size()
                        ? "· " + capitalizeMessage(leftColumnNames.get(i))
                        + getAnsi256Color(themeColor_2)
                        + " [" + leftColumnCodes.get(i).toUpperCase() + "]"
                        : "";

                String rightEntry = i < rightColumnNames.size()
                        ? "· " + capitalizeMessage(rightColumnNames.get(i))
                        + getAnsi256Color(themeColor_2)
                        + " [" + rightColumnCodes.get(i).toUpperCase() + "]"
                        : "";

                out.printf(alignment(38) + getAnsi256Color(layoutColor) + "%-40s"
                        + alignment(10) + getAnsi256Color(layoutColor) + "          %-40s%n", leftEntry, rightEntry);
            }

            modifyMessage('n', 2);
            border();
        } catch (Exception e) {
            errorFormatting("Error of showing list");
        }
    }

    //Getting an actual cryptocurrency prices
    private static void getCryptocurrencyPrice(String cryptocurrencyCode, String fiatCurrencyCode) {
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

    //Show settings
    private static void extensionSettings(){
        modifyMessage('n', 2);
        try{

        }
        catch (Exception e){
            errorFormatting("Unexpected error.");
        }
    }

    //Formating the error outputs
    private static void errorFormatting(String text) {
        modifyMessage('n', 1);
        message(text, rejectionColor, 58, 0, out::print);
        modifyMessage('n', 1);
    }
}