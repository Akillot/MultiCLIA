package extensions.finance;

import lombok.Getter;
import java.util.*;

public class CryptoConfigs {

    static final LinkedList<String> cryptocurrencyCodes = new LinkedList<>() {{
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

    static final LinkedList<String> cryptocurrencyName = new LinkedList<>() {{
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

    @Getter
    static String[] cryptoLogo = {
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

    static final Scanner scanner = new Scanner(System.in);
    static String cryptocurrencyCode;
    static String fiatCurrencyCode;

    static int themeColor_1 = 147;
    static int themeColor_2 = 183;
    static int layoutColor = 15;

    static int acceptanceColor = 46;
    static int rejectionColor = 196;

    @Getter
    private static final String version = "1.1.4";

    static final Map<String, String> CRYPTO_MAP = new HashMap<>() {{
        for (int i = 0; i < cryptocurrencyCodes.size() && i < cryptocurrencyName.size(); i++) {
            put(cryptocurrencyCodes.get(i), cryptocurrencyName.get(i));
        }
    }};
}