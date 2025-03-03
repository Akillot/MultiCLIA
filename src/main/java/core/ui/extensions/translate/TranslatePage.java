package core.ui.extensions.translate;

import com.deepl.api.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Scanner;

import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.TextConfigs.insertControlChars;
import static core.ui.essential.configs.TextConfigs.message;
import static java.lang.System.out;

public class TranslatePage {

    private static final String API_KEY;

    static {
        Dotenv dotenv = Dotenv.load();
        API_KEY = dotenv.get("DEEPL_API_KEY");

        if(API_KEY == null || API_KEY.isEmpty()) {
            insertControlChars('n',1);
            message("Translate is unavailable. Check your API Key.", layoutColor,
                    getDefaultTextAlignment(),getDefaultDelay(),out::println);
        }
    }

    public static void displayTranslatePage() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter text to translate:");
        String textToTranslate = scanner.nextLine();

        System.out.println("Enter target language (e.g., 'EN' for English, 'RU' for Russian):");
        String targetLanguage = scanner.nextLine().toUpperCase();

        try {
            String translatedText = translateText(textToTranslate, targetLanguage);
            System.out.println("Translation: " + translatedText);
        } catch (Exception e) {
            System.err.println("Translation error: " + e.getMessage());
        }
    }

    private static String translateText(String text, String targetLanguage) throws Exception {
        Translator translator = new Translator(API_KEY);
        TextResult result = translator.translateText(text, null, targetLanguage);
        return result.getText();
    }
}
