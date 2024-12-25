package extensions.internet;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.*;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.*;
import static java.lang.System.out;

public class SearcherConfigs {

    private static int firstThemeColor = 99;
    private static int secondThemeColor = 121;
    private static int layoutColo = 15;

    private static int acceptanceColor = 46;
    private static int rejectionColor = 196;


    public static void searcher() {

        modifyMessage('n', 2);
        switchLogo(searcherLogo,32);
        marginBorder();
        modifyMessage('n', 1);
        alert("Example",getAnsi256Color(layoutColo)
                + ": '" + getAnsi256Color(firstThemeColor) + "github.com"
                + getAnsi256Color(layoutColo) + "'",58);

        modifyMessage('n', 1);

        out.print(alignment(58) + getAnsi256Color(firstThemeColor) + "Enter domain"
                + getAnsi256Color(layoutColo) + " (or type '" + getAnsi256Color(rejectionColor) + "exit"
                + getAnsi256Color(layoutColo) + "' to quit): " + RESET);

        while (true) {
            modifyMessage('n', 1);
            String domainInput = scanner.nextLine().toLowerCase();

            modifyMessage('n', 1);

            if (domainInput.equals("exit")) {
                terminateExtension();
                modifyMessage('n', 1);
                break;
            }

            String domain = "https://" + domainInput;
            displayConfirmation("Enter","to open and","to skip");
            choice("Show URL", SearcherConfigs.displayFullURl(domain),
                    systemFirstColor, systemLayoutColor, systemFirstColor);
            modifyMessage('n', 1);
            displayConfirmation("Enter","to open and","to skip");
            choice(domainInput, openUri(domain),
                    systemFirstColor, systemLayoutColor, systemFirstColor);
        }
    }

    @Contract(pure = true)
    private static @NotNull Runnable displayFullURl(String domain) {
        return () -> {
            message("Full URL: " + domain, layoutColo, 58, 0, out::print);
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