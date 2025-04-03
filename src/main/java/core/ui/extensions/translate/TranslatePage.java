package core.ui.extensions.translate;

import com.deepl.api.*;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.TextConfigs.*;
import static java.lang.System.out;

public class TranslatePage {

    private static final String API_KEY;
    @Setter @Getter
    private static int translatedTextColor = 219;

    static {
        Dotenv dotenv = Dotenv.load();
        API_KEY = dotenv.get("DEEPL_API_KEY");

        if (API_KEY == null || API_KEY.isEmpty()) {
            insertControlChars('n', 1);
            message("Translate is unavailable. Check your API Key.", layoutColor,
                    getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }

    public static void displayTranslatePage() {
        Scanner scanner = new Scanner(System.in);
        marginBorder(1,2);

        while (true) {
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter text to translate [Enter "
                    + getColor(mainColor) + "q" + getColor(layoutColor) + " to quit]: ");
            String textToTranslate = scanner.nextLine();

            if (textToTranslate.equalsIgnoreCase("q")) {
                marginBorder(2,2);
                message("Status: " + getColor(acceptanceColor) + "✓", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                message("Exiting Translate Page" + getColor(layoutColor) + ". "
                        + getColor(mainColor) + "You are now in main menu" + getColor(layoutColor) + ".",
                        mainColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                marginBorder(2,1);
                break;
            }

            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter target language" +
                    " [e.g., '" + getColor(mainColor) + "EN-US" + getColor(layoutColor) + "' for English, "
                    + getColor(mainColor) + "UK" + getColor(layoutColor) + " for Ukrainian]: ");
            String targetLanguage = scanner.nextLine().toUpperCase();

            try {
                String translatedText = translateText(textToTranslate, targetLanguage);
                message("Translation: " + getColor(translatedTextColor) + translatedText,
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            } catch (Exception e) {
                insertControlChars('n', 1);
                message("Translation error: " + e.getMessage(),
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }
        }
    }

    private static String translateText(String text, String targetLanguage) throws Exception {
        Translator translator = new Translator(API_KEY);
        TextResult result = translator.translateText(text, null, targetLanguage);
        return result.getText();
    }
}