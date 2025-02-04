package core.pages;

import com.theokanning.openai.model.Model;
import com.theokanning.openai.service.OpenAiService;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;
import java.util.Scanner;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;
import static core.logic.ChatGPTClient.sendMessage;
import static core.logic.CommandManager.exitPage;
import static core.logic.CommandManager.mainMenuRerun;
import static core.ui.DisplayManager.clearTerminal;
import static core.ui.DisplayManager.scanner;
import static java.lang.System.out;

public class AiPage {

    private static String coloredChatGptLogo =
            getColor(204) + "C" + getColor(110) + "h"
                    + getColor(205) + "a" + getColor(208) + "t"
                    + getColor(161) + "G" + getColor(207) + "P"
                    + getColor(217) + "T";

    public static void displayAiPage(){
        marginBorder(1,2);
        message("AI:", sysLayoutColor, getDefaultTextAlignment(), 0, out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, getSearchingLineAlignment(), false,
                    getColor(sysLayoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "ask chatgpt", "/ac" -> runChatGpt();
                case "info", "/i" -> displayInfo();
                case "rerun", "/rr" -> {
                    insertControlChars('n',1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
                case "list of commands", "/lc" -> displayListOfCommands();
                case "exit", "/e" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands(){
        insertControlChars('n',1);
        message("·  Ask ChatGPT ["  + getColor(sysMainColor)
                + "/ac" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), 0, out::print);

        message("·  Info ["  + getColor(sysMainColor)
                + "/i" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), 0, out::print);

        message("·  List Of Commands ["  + getColor(sysMainColor)
                + "/lc" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), 0, out::print);

        message("·  Exit [" + getColor(sysMainColor)
                + "/e" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
    }

    private static void runChatGpt() {
        Scanner scanner = new Scanner(System.in);
        try {
            marginBorder(1, 2);
            message("··· " + getColor(sysLayoutColor) + "Powered by OpenAI " + coloredChatGptLogo + RESET + getColor(sysLayoutColor) + " ···"
                    , sysLayoutColor, getDefaultTextAlignment(), 0, out::print);

            while (true) {
                insertControlChars('n', 1);
                out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "Enter prompt [or "
                        + getColor(sysMainColor) + "exit" + getColor(sysLayoutColor) + " to quit]: ");
                String userMessage = scanner.nextLine();

                if (userMessage.equalsIgnoreCase("exit")) {
                    message("Status: " + getColor(sysAcceptanceColor) + "✓", sysLayoutColor,getDefaultTextAlignment(),0,out::print);
                    message("You are in ai page" + getColor(sysLayoutColor) + ".", sysMainColor,
                            getDefaultTextAlignment(), 0, out::println);
                    break;
                }

                if (userMessage.isEmpty()) {
                    insertControlChars('n', 1);
                    message("Prompt should not be empty.", sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
                    marginBorder(1, 1);
                    continue;
                }

                String response = sendMessage(userMessage);

                insertControlChars('n', 1);
                out.print(alignment(getDefaultTextAlignment()) + coloredChatGptLogo + getColor(sysLayoutColor) + ": ");
                slowMotionText(50, getDefaultTextAlignment(), false,
                        getColor(sysLayoutColor) + response, "");
                insertControlChars('n', 1);
            }
        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error: " + e.getMessage(), sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
        }
    }

    private static void displayInfo(){
        insertControlChars('n',1);
        String apiKey = Dotenv.load().get("OPENAI_API_KEY");
        assert apiKey != null;
        OpenAiService service = new OpenAiService(apiKey);

        List<Model> models = service.listModels();

        message("Models", sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
        for (Model model : models) {
            message("·  " + model.getId(), sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
        }
    }
}