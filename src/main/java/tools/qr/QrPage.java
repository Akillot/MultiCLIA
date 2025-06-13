package tools.qr;

import core.Page;
import org.jetbrains.annotations.Nullable;

import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.DisplayManager.*;
import static core.CommandManager.*;
import static core.ui.configs.TextConfigs.*;
import static tools.qr.QrCodeGenerator.generateAsciiQr;
import static tools.qr.QrCodeGenerator.generateQR;
import static java.lang.System.out;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QrPage extends Page {

    public static Path SAVE_DIRECTORY = Paths.get("saved_qr_codes");
    private static final int MIN_SIZE = 10;
    private static final int MAX_SIZE = 300;
    private static final String DEFAULT_FORMAT = ".png";

    public int size = 50;
    public String format = DEFAULT_FORMAT;

    private static final String[] VALID_FORMATS = {
            ".png", ".jpg", ".jpeg", ".gif",
            ".bmp", ".svg", ".tiff", ".webp",
            ".eps", ".pdf", ".ico"
    };

    private final String[][] commands = {
            {"Generate QR code", "qr"},
            {"Save QR code as image", "sq"},
            {"Modify QR size", "ms"},
            {"Modify image format", "mf"},
            {"Info", "i"},
            {"Restart", "r"},
            {"Clear", "cl"},
            {"Help", "h"},
            {"Quit", "q"}
    };

    private static final String[] QR_ASCII_LOGO = {
            "╔═════════════════════╗",
            "║                     ║",
            "║   ██████╗ ██████╗   ║",
            "║  ██╔═══██╗██╔══██╗  ║",
            "║  ██║   ██║██████╔╝  ║",
            "║  ██║▄▄ ██║██╔══██╗  ║",
            "║  ╚██████╔╝██║  ██║  ║",
            "║   ╚══▀▀═╝ ╚═╝  ╚═╝  ║",
            "║                     ║",
            "╚═════════════════════╝"
    };

    public void displayMenu() {
        marginBorder(1, 2);
        clearTerminal();
        displayLogo(getDefaultTextAlignment(), QR_ASCII_LOGO);
        insertControlChars('n',2);

        displayCurrentSettings();
        displayListOfCommands(commands);

        while (true) {
            slowMotionText(getDefaultDelay(),
                    getSearchingLineAlignment(),
                    false,
                    getColor(getLayoutColor()) + getSearchingArrow(),
                    "");

            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "generate qr code", "qr" -> generateQrCode();
                case "save qr code as image", "sq" -> saveQrCodeAsImage();
                case "modify qr size", "ms" -> modifyQrCodeSize();
                case "modify image format", "mf" -> modifyQrCodeFormat();
                case "info", "i" -> {
                    insertControlChars('n',1);
                    displayCurrentSettings();
                    insertControlChars('n',1);
                }

                case "restart", "r" -> clearAndRestartApp();
                case "clear", "cl" -> clearTerminal();
                case "help", "h" -> displayListOfCommands(commands);
                case "quit", "q", "exit", "e" -> {
                    exitPageFormatting();
                    clearAndRestartApp();
                    return;
                }
                default -> showInvalidCommand(input);
            }
        }
    }

    private void displayCurrentSettings() {
        message("[Size: "
                        + getColor(getMainColor()) + size + getColor(getLayoutColor())
                        + " | Format: " + getColor(getMainColor())
                        + format + getColor(getLayoutColor()) + "]",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);
    }

    public void generateQrCode() {
        String url = promptForUrl();
        if (url == null) return;

        try {
            insertControlChars('n', 1);
            generateAsciiQr(url, size);
            insertControlChars('n', 1);
        } catch (Exception e) {
            showError("Error generating QR code: " + e.getMessage());
        }
    }

    private void saveQrCodeAsImage() {
        String url = promptForUrl();
        if (url == null) return;

        try {
            ensureSaveDirectoryExists();
            String fileName = "qr_" + System.currentTimeMillis() + format;
            Path filePath = SAVE_DIRECTORY.resolve(fileName);

            generateQR(url, filePath.toString(), size, size);
            showSuccess("QR code saved successfully as: " + filePath);
        } catch (Exception e) {
            showError("Error saving QR code: " + e.getMessage());
        }
    }

    @Nullable
    private String promptForUrl() {
        insertControlChars('n', 1);

        out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor())
                + "Enter URL [include " + getColor(getMainColor())
                + "http:// " + getColor(getLayoutColor()) + "or " + getColor(getMainColor())
                + " https://" + getColor(getLayoutColor()) + "]: ");

        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            showError("Error: URL cannot be empty");
            return null;
        }

        if (!input.startsWith("http://") && !input.startsWith("https://"))
            input = "https://" + input; return input;
    }

    private void modifyQrCodeSize() {
        out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) +
                String.format("Enter new size (%d-%d): ", MIN_SIZE, MAX_SIZE));

        try {
            int newSize = Integer.parseInt(scanner.nextLine().trim());
            if (newSize < MIN_SIZE || newSize > MAX_SIZE) {
                showError(String.format("Size must be between %d and %d", MIN_SIZE, MAX_SIZE));
            } else {
                size = newSize;
                showSuccess("QR code size updated to: " + size);
            }
        } catch (NumberFormatException e) {
            showError("Invalid input. Please enter a number.");
        }
    }

    private void modifyQrCodeFormat() {
        showFormatsHelp();
        out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) +
                "Enter image format [or 'help' to show formats]: ");

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equalsIgnoreCase("help")) {
                showFormatsHelp();
                out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) +
                        "Enter format: ");
                continue;
            }

            if (input.isEmpty()) {
                showError("Format cannot be empty. Using default [" + DEFAULT_FORMAT + "]");
                return;
            }

            if (!input.startsWith(".")) input = "." + input;

            for (String validFormat : VALID_FORMATS) {
                if (input.equals(validFormat)) {
                    format = input;
                    showSuccess("Image format updated to: " + format);
                    return;
                }
            }

            showError("Unsupported format. Supported formats: " + String.join(", ", VALID_FORMATS));
            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) +
                    "Try again or 'help' for options: ");
        }
    }

    private void showFormatsHelp() {
        insertControlChars('n',1);
        message("Supported formats:", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        out.println(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor())
                + "·  PNG [" + getColor(getMainColor()) + ".png" + getColor(getLayoutColor())
                + "] - Best for QR codes [default]");

        out.println(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor())
                + "·  JPEG [" + getColor(getMainColor()) + ".jpg"
                + getColor(getLayoutColor()) + "/" + getColor(getMainColor())
                + ".jpeg" + getColor(getLayoutColor())  + "] - Smaller file size");

        out.println(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor())
                + "·  GIF [" + getColor(getMainColor()) + ".gif"
                + getColor(getLayoutColor()) + "] - For animated QR codes");

        out.println(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor())
                + "·  SVG [" + getColor(getMainColor()) + ".svg"
                + getColor(getLayoutColor()) + "] - Vector format [scalable]");

        out.println(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor())
                + "·  WebP [" +  getColor(getMainColor()) + ".webp"
                + getColor(getLayoutColor()) + "] - Modern efficient format");

        out.println(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor())
                + "·  PDF [" +  getColor(getMainColor()) + ".pdf"
                + getColor(getLayoutColor()) + "] - For document embedding");

        out.println(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor())
                + "·  ICO [" +  getColor(getMainColor()) + ".ico"
                + getColor(getLayoutColor()) + "] - For website favicons\n");
    }

    private void ensureSaveDirectoryExists() throws Exception {
        if (!Files.exists(SAVE_DIRECTORY)) Files.createDirectories(SAVE_DIRECTORY);
    }

    private void showSuccess(String message) {
        message(getColor(getAcceptanceColor()) + "✓ " + getColor(getLayoutColor()) + message,
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);
    }

    private void showError(String message) {
        message("Error: " + message + RESET,
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);
    }

    private void exitApplication() {
        exitPageFormatting();
    }

    private void showHelp() {
        displayListOfCommands(commands);
    }

    private void showInvalidCommand(String input) {
        showError("Unknown command: '" + input + "'. Type 'help' for available commands.");
    }

    @Override
    protected void displayListOfCommands(String[][] commands) {
        super.displayListOfCommands(commands);
    }
}