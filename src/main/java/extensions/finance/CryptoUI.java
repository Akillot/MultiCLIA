package extensions.finance;

import java.util.List;

import static core.configs.AppearanceConfigs.*;
import static core.logic.CommandManager.*;
import static core.configs.TextConfigs.*;
import static core.configs.TextConfigs.alignment;
import static extensions.finance.CryptoConfigs.*;
import static extensions.finance.CryptoService.*;
import static extensions.finance.CryptoService.getCryptocurrencyPrice;
import static java.lang.System.out;

public class CryptoUI {

    //Main menu of extension
    public static void displayCryptoMenu() {
        marginBorder(1,2);
        switchLogoRandomly(cryptoLogo,8);
        marginBorder(1,2);

        displayConfirmation("Enter","y","+",
                "to open and","n","-","to skip",
                systemAcceptanceColor, systemRejectionColor, systemLayoutColor);

        choice("List of cryptocurrencies", CryptoUI::displayListOfCryptocurrencies,
                themeColor_1, layoutColor, rejectionColor);
        modifyMessage('n',2);

        choice("Exchanger", CryptoUI::exchanger,
                themeColor_1, layoutColor, rejectionColor);
        modifyMessage('n',2);

        choice("Price tracker", CryptoUI::currencyPriceTracker,
                themeColor_1, layoutColor, rejectionColor);
        modifyMessage('n',2);

        choice("Info", CryptoUI::displayInfo,
                themeColor_1, layoutColor, rejectionColor);
        marginBorder(2,1);
    }

    //Exchanger method
    private static void exchanger() {
        modifyMessage('n',1);
        alert("i", "Type '" + getAnsi256Color(themeColor_1) + "exit"
                        + getAnsi256Color(layoutColor) + "' to\n" + alignment(58) + "quit the extension.",
                58, themeColor_1, layoutColor);
        modifyMessage('n',1);

        while (true) {
            modifyMessage('n', 1);
            out.print(alignment(58) + getAnsi256Color(layoutColor) + "Cryptocurrency code: " + RESET);
            cryptocurrencyCode = scanner.nextLine().trim().toLowerCase();

            if (cryptocurrencyCode.equalsIgnoreCase("exit")) {
                modifyMessage('n', 1);
                terminateExtension(themeColor_1,acceptanceColor,layoutColor);
            }

            if (!CRYPTO_MAP.containsKey(cryptocurrencyCode)) {
                message("Invalid cryptocurrency code: " + cryptocurrencyCode, rejectionColor, 58, 0, out::print);
                continue;
            }

            out.print(alignment(58) + getAnsi256Color(layoutColor) + "Fiat currency code: " + RESET);
            fiatCurrencyCode = scanner.nextLine().trim().toLowerCase();

            if (fiatCurrencyCode.equalsIgnoreCase("exit")) {
                terminateExtension(themeColor_1,acceptanceColor,layoutColor);
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
                        + getAnsi256Color(layoutColor) + "' to quit the extension.\n"
                        + alignment(58) + "You cannot exit this mode while tracking is in progress.",
                58, themeColor_1, layoutColor);

        while (true) {
            modifyMessage('n', 1);
            out.print(alignment(58) + getAnsi256Color(layoutColor) + "Cryptocurrency code: " + RESET);
            cryptocurrencyCode = scanner.nextLine().trim().toLowerCase();

            if (cryptocurrencyCode.equalsIgnoreCase("exit")) {
                terminateExtension(themeColor_1,acceptanceColor,layoutColor);
            }

            if (!CRYPTO_MAP.containsKey(cryptocurrencyCode)) {
                message("Invalid cryptocurrency code: " + cryptocurrencyCode, rejectionColor, 58, 0, out::print);
                continue;
            }

            out.print(alignment(58) + getAnsi256Color(layoutColor) + "Fiat currency code: " + RESET);
            fiatCurrencyCode = scanner.nextLine().trim().toLowerCase();

            if (fiatCurrencyCode.equalsIgnoreCase("exit")) {
                terminateExtension(themeColor_1,acceptanceColor,layoutColor);
            }

            if (fiatCurrencyCode.isEmpty()) {
                message("Fiat currency code cannot be empty.", rejectionColor, 58, 0, out::print);
                continue;
            }

            out.print(alignment(58) + getAnsi256Color(layoutColor) + "Duration in minutes: " + RESET);

            double duration;
            try {
                duration = Double.parseDouble(scanner.nextLine().trim()) * 60000;
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

            modifyMessage('n',1);
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
            modifyMessage('n',2);
            border();
        } catch (Exception e) {
            errorFormatting("Error of showing list");
        }
    }

    public static void displayInfo() {
        modifyMessage('n', 1);
        message("Name: " + getAnsi256Color(themeColor_2) + "Crypto", layoutColor, 58, 0, out::print);
        message("Type: " + getAnsi256Color(themeColor_2) + "Default extension", layoutColor, 58, 0, out::print);
        message("Version: " +  getAnsi256Color(themeColor_2) + getVersion(), layoutColor, 58, 0, out::print);
        message("Author: " + getAnsi256Color(themeColor_2) + "Nick Zozulia", layoutColor, 58, 0, out::print);
    }
}
