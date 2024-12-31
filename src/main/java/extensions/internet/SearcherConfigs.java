package extensions.internet;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static core.logic.AppearanceConfigs.*;
import static core.logic.CommandManager.*;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.*;
import static java.lang.System.out;

public class SearcherConfigs {

    private static int themeColor_1 = 75;
    private static int themeColor_2 = 111;
    private static int layoutColor = 15;

    private static int acceptanceColor = 46;
    private static int rejectionColor = 196;


    public static void searcher() {

        modifyMessage('n', 2);
        switchLogoRandomly(searcherLogo,32);
        marginBorder();
        modifyMessage('n', 1);
        alert("Example",getAnsi256Color(layoutColor)
                + ": '" + getAnsi256Color(themeColor_1) + "github.com"
                + getAnsi256Color(layoutColor) + "'",58);

        modifyMessage('n', 1);

        while (true) {
            modifyMessage('n', 1);
            message("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",layoutColor, 58,0,out::print);
            modifyMessage('n', 2);

            out.print(alignment(58) + getAnsi256Color(themeColor_1) + "Enter domain"
                    + getAnsi256Color(layoutColor) + " (Type '" + getAnsi256Color(rejectionColor) + "exit"
                    + getAnsi256Color(layoutColor) + "' to quit): " + RESET);
            String domainInput = scanner.nextLine().toLowerCase();

            modifyMessage('n', 1);

            if (domainInput.equals("exit") || domainInput.equals("e") || domainInput.equals("quit") || domainInput.equals("q")) {
                terminateExtension();
                modifyMessage('n', 1);
                break;
            }

            String domain = "https://" + domainInput;

            displayConfirmation("Enter","to open and","to skip",
                    acceptanceColor,rejectionColor,layoutColor);
            choice("Show URL", SearcherConfigs.displayFullURl(domain),
                    themeColor_1,rejectionColor,layoutColor);

            modifyMessage('n', 1);

            displayConfirmation("Enter","to open and","to skip",
                    acceptanceColor,rejectionColor,layoutColor);
            choice(domainInput, openUri(domain),
                    themeColor_1,rejectionColor,layoutColor);
        }
    }

    @Contract(pure = true)
    private static @NotNull Runnable displayFullURl(String domain) {
        return () -> {
            message("Full URL: " + getAnsi256Color(75) + domain, layoutColor, 58, 0, out::print);
        };
    }

    private static String[] searcherLogo = {
            " .oooooo..o                                        oooo                           ",
            "d8P'    `Y8                                        `888                           ",
            "Y88bo.       .ooooo.   .oooo.   oooo d8b  .ooooo.   888 .oo.    .ooooo.  oooo d8b ",
            " `\"Y8888o.  d88' `88b `P  )88b  `888\"\"8P d88' `\"Y8  888P\"Y88b  d88' `88b `888\"\"8P ",
            "     `\"Y88b 888ooo888  .oP\"888   888     888        888   888  888ooo888  888    ",
            "oo     .d8P 888    .o d8(  888   888     888   .o8  888   888  888    .o  888     ",
            "8\"\"88888P'  `Y8bod8P' `Y888\"\"8o d888b    `Y8bod8P' o888o o888o `Y8bod8P' d888b    ",
            " "
    };
}