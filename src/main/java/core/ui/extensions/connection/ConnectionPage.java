package core.ui.extensions.connection;

import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.ui.essential.configs.DisplayManager.scanner;
import static core.logic.CommandManager.*;
import static core.ui.essential.configs.TextConfigs.*;
import static core.ui.essential.pages.EasterEggPage.displayEasterEgg;
import static core.ui.extensions.connection.QrCodeGenerator.generateAsciiQr;
import static core.ui.extensions.connection.QrCodeGenerator.generateQR;
import static java.lang.System.out;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConnectionPage {

    private static int size = 45;
    private static final Path SAVE_DIRECTORY = Paths.get("saved_qr_codes");

    public static void displayConnectionPage() {
        marginBorder(1, 2);
        message("Connection:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        displayListOfCommands();

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(layoutColor) + searchingArrow, "");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "make qr code", "/qr" -> generateQrCode();
                case "save qr code", "/sq" -> saveQrCodeAsImage();
                case "change size", "/cs" -> modifyQrCodeSize();
                case "restart", "/rs" -> {
                    insertControlChars('n', 1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
                case "list", "/ls" -> displayListOfCommands();
                case "easteregg", "/ee" -> displayEasterEgg();
                case "quit", "/q" -> {
                    exitPage();
                    return;
                }
                default -> insertControlChars('n', 1);
            }
        }
    }

    private static void displayListOfCommands() {
        insertControlChars('n', 1);
        String[][] commands = {
                {"Make QR code", "/qr"},
                {"Save QR code as PNG", "/sq"},
                {"Change size", "/cs"},
                {"Restart", "/rs"},
                {"Clear terminal", "/cl"},
                {"List", "/ls"},
                {"Quit", "/q"}
        };

        for (String[] command : commands) {
            message("·  " + command[0] + " [" + getColor(mainColor) + command[1] + getColor(layoutColor) + "]",
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
        insertControlChars('n', 1);
    }

    private static void generateQrCode() {
        insertControlChars('n', 1);
        out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter absolute link: "
                + getColor(mainColor) + "https://");

        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            insertControlChars('n', 1);
            message("Error: input is empty", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            return;
        }

        input = "https://" + input;
        insertControlChars('n', 1);
        try {
            generateAsciiQr(input, size);
            insertControlChars('n', 1);
        } catch (Exception e) {
            message("Error generating QR code", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }

    private static void saveQrCodeAsImage() {
        insertControlChars('n', 1);
        out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter absolute link: "
                + getColor(mainColor) + "https://");

        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            insertControlChars('n', 1);
            message("Error: input is empty", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            return;
        }

        input = "https://" + input;

        try {
            if (!Files.exists(SAVE_DIRECTORY)) {
                Files.createDirectories(SAVE_DIRECTORY);
            }

            String fileName = "qr_" + System.currentTimeMillis() + ".png";
            Path filePath = SAVE_DIRECTORY.resolve(fileName);

            generateQR(input, filePath.toString(), size, size);
        } catch (Exception e) {
            message("Error saving QR code: " + e.getMessage(), layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }

    private static void modifyQrCodeSize() {
        insertControlChars('n', 1);
        out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter new size of QR code: ");

        try {
            int newSize = Integer.parseInt(scanner.nextLine().trim());

            if (newSize <= 0 || newSize >= 100) {
                message("Error: invalid size (must be 1-99)", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            } else {
                size = newSize;
                insertControlChars('n', 1);
                message("Status: " + getColor(acceptanceColor) + "✓", layoutColor,
                        getDefaultTextAlignment(), getDefaultDelay(), out::print);
                message("New size is: " + getColor(mainColor) + size + getColor(layoutColor) + ".",
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }
        } catch (NumberFormatException e) {
            insertControlChars('n', 1);
            message("Error: invalid input (enter a number)", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }
}
