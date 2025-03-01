package core.ui.extensions.connection;

import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.ui.essential.configs.DisplayManager.scanner;
import static core.logic.CommandManager.*;
import static core.ui.essential.configs.TextConfigs.*;
import static core.ui.extensions.connection.QrCodeGenerator.generateAsciiQr;
import static java.lang.System.out;

public class ConnectionPage {

    public static void displayPage() {
        marginBorder(1, 2);
        message("Connection:", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        displayListOfCommands();

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(sysLayoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "make qr code", "/qr" -> generateQrCode();
                case "restart", "/rs" -> {
                    insertControlChars('n', 1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
                case "list", "/ls" -> displayListOfCommands();
                case "quit", "/q" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands()  {
        insertControlChars('n', 1);
        message("·  Make qr code [" + getColor(sysMainColor)
                + "/qr" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Rerun [" + getColor(sysMainColor)
                + "/rr" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Clear terminal [" + getColor(sysMainColor)
                + "/cl" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  List [" + getColor(sysMainColor)
                + "/ls" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Exit [" + getColor(sysMainColor)
                + "/e" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    private static void generateQrCode() {
        insertControlChars('n', 1);
        out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "Enter absolute link: "
                + getColor(sysMainColor) + "https://");
        String input = scanner.nextLine().toLowerCase();

        if(input.isEmpty()){
            insertControlChars('n', 1);
            message("Error: input is empty ", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }

        input = "https://" + input;
        insertControlChars('n', 1);
        try {
            generateAsciiQr(input, 40);
            insertControlChars('n', 1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
