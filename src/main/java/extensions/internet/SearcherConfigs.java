package extensions.internet;

import lombok.Getter;
import org.jetbrains.annotations.Contract;

import static core.logic.AppearanceConfigs.*;
import static core.logic.TextConfigs.*;
import static java.lang.System.out;

public class SearcherConfigs {

    static final int themeColor_1 = 75;
    private static final int themeColor_2 = 111;
    static final int layoutColor = 15;

    static final int acceptanceColor = 46;
    static final int rejectionColor = 196;

    @Getter
    private static final String version = "1.1.1";

    @Getter
    static final String[] searcherLogo = {
            " .oooooo..o                                        oooo                           ",
            "d8P'    `Y8                                        `888                           ",
            "Y88bo.       .ooooo.   .oooo.   oooo d8b  .ooooo.   888 .oo.    .ooooo.  oooo d8b ",
            " `\"Y8888o.  d88' `88b `P  )88b  `888\"\"8P d88' `\"Y8  888P\"Y88b  d88' `88b `888\"\"8P ",
            "     `\"Y88b 888ooo888  .oP\"888   888     888        888   888  888ooo888  888    ",
            "oo     .d8P 888    .o d8(  888   888     888   .o8  888   888  888    .o  888     ",
            "8\"\"88888P'  `Y8bod8P' `Y888\"\"8o d888b    `Y8bod8P' o888o o888o `Y8bod8P' d888b    "
    };

    @Contract(pure = true)
    public static void displayFullURL(String domain) {
        message("Full URL: " + getAnsi256Color(themeColor_1) + domain, layoutColor, 58, 0, out::print);
    }
}
