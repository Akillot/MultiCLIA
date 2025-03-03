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

        if(API_KEY == null || API_KEY.isEmpty()) {
            insertControlChars('n',1);
            message("Translate is unavailable. Check your API Key.", layoutColor,
                    getDefaultTextAlignment(),getDefaultDelay(),out::println);
        }
    }

    public static void displayTranslatePage() {
        Scanner scanner = new Scanner(System.in);

        insertControlChars('n',1);
        out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter text to translate: ");
        String textToTranslate = scanner.nextLine();

        out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter target language" +
                " [e.g., '" + getColor(mainColor) + "EN" + getColor(layoutColor) + "' for English, "
                + getColor(mainColor) + "UK" + getColor(layoutColor) + " for Ukrainian]: ");
        String targetLanguage = scanner.nextLine().toUpperCase();

        try {
            String translatedText = translateText(textToTranslate, targetLanguage);
            message("Translation: " + getColor(translatedTextColor) + translatedText,layoutColor,getDefaultTextAlignment(),getDefaultDelay(),out::println);
        } catch (Exception e) {
            insertControlChars('n',1);
            message("Translation error: " + e.getMessage(), layoutColor,getDefaultTextAlignment(),getDefaultDelay(),out::println);
        }
    }

    private static String translateText(String text, String targetLanguage) throws Exception {
        Translator translator = new Translator(API_KEY);
        TextResult result = translator.translateText(text, null, targetLanguage);
        return result.getText();
    }
}
