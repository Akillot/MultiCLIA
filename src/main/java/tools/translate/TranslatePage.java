package tools.translate;

import com.deepl.api.*;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

import static core.CommandManager.clearAndRestartApp;
import static core.CommandManager.exitPageFormatting;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.DisplayManager.clearTerminal;
import static core.ui.configs.DisplayManager.displayLogo;
import static core.ui.configs.TextConfigs.*;
import static java.lang.System.out;

public class TranslatePage {

    private static final String API_KEY;

    @Setter @Getter
    private static int translatedTextColor = 219;

    private static final String[] TRANSLATE_ASCII_LOGO = {
            "╔════════════════════════════════════════════════════════════════════════════════╗",
            "║                                                                                ║",
            "║  ████████╗██████╗  █████╗ ███╗   ██╗███████╗██╗      █████╗ ████████╗███████╗  ║",
            "║  ╚══██╔══╝██╔══██╗██╔══██╗████╗  ██║██╔════╝██║     ██╔══██╗╚══██╔══╝██╔════╝  ║",
            "║     ██║   ██████╔╝███████║██╔██╗ ██║███████╗██║     ███████║   ██║   █████╗    ║",
            "║     ██║   ██╔══██╗██╔══██║██║╚██╗██║╚════██║██║     ██╔══██║   ██║   ██╔══╝    ║",
            "║     ██║   ██║  ██║██║  ██║██║ ╚████║███████║███████╗██║  ██║   ██║   ███████╗  ║",
            "║     ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚══════╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝  ║",
            "║                                                                                ║",
            "╚════════════════════════════════════════════════════════════════════════════════╝"
    };

    static {
        Dotenv dotenv = Dotenv.load();
        API_KEY = dotenv.get("DEEPL_API_KEY");
    }

    public static void displayTranslatePage() {
        Scanner scanner = new Scanner(System.in);
        marginBorder(1,2);
        clearTerminal();
        displayLogo(getDefaultTextAlignment(),TRANSLATE_ASCII_LOGO);
        insertControlChars('n',2);

        while (true) {
            out.print(alignment(getDefaultTextAlignment())
                    + getColor(getLayoutColor()) + "Enter text to translate [Enter "
                    + getColor(getMainColor()) + "q" + getColor(getLayoutColor()) + " to quit]: ");

            String textToTranslate = scanner.nextLine();

            if (textToTranslate.equalsIgnoreCase("q")
                    || textToTranslate.equalsIgnoreCase("quit")) {

                exitPageFormatting();
                clearAndRestartApp();
                break;
            }

            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter target language"
                    + " [e.g., '" + getColor(getMainColor()) + "EN-US"
                    + getColor(getLayoutColor()) + "' for English, "
                    + getColor(getMainColor()) + "UK"
                    + getColor(getLayoutColor()) + " for Ukrainian]: ");

            String targetLanguage = scanner.nextLine().toUpperCase();

            try {
                String translatedText = translateText(textToTranslate, targetLanguage);
                message("Translation: " + getColor(translatedTextColor) + translatedText,
                        getLayoutColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::println);

            } catch (Exception e) {
                insertControlChars('n', 1);
                message("Translation error: " + e.getMessage(),
                        getLayoutColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::println);
            }
        }
    }

    private static String translateText(String text, String targetLanguage) throws Exception {
        Translator translator = new Translator(API_KEY);
        TextResult result = translator.translateText(text, null, targetLanguage);
        return result.getText();
    }
}