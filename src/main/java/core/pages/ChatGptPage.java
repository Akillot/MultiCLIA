package core.pages;

import java.util.Scanner;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;
import static core.logic.ChatGPTClient.sendMessage;
import static java.lang.System.out;

public class ChatGptPage {
    public static void displayChatGptPage() {
        Scanner scanner = new Scanner(System.in);
        try {
            marginBorder(1,2);
            message("··· " + getColor(sysMainColor) + "Powered by OpenAI ChatGPT" + RESET + getColor(sysLayoutColor) + " ···"
                    , sysLayoutColor, getDefaultTextAlignment(), 0, out::print);

            while (true) {
                insertControlChars('n', 1);
                out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "Enter prompt [or "
                        + getColor(sysMainColor) + "exit" + getColor(sysLayoutColor) + " to quit]: ");
                String userMessage = scanner.nextLine();

                if (userMessage.equalsIgnoreCase("exit")) {
                    marginBorder(2,2);
                    message("Status: " + getColor(sysAcceptanceColor) + "✓", sysLayoutColor, getDefaultTextAlignment(), 0, out::print);
                    message("You are in main menu" + getColor(sysLayoutColor) + ".", sysMainColor,
                            getDefaultTextAlignment(), 0, out::println);
                    marginBorder(1,1);
                    break;
                }

                if (userMessage.isEmpty()) {
                    insertControlChars('n', 1);
                    message("Prompt should not be empty.", sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
                    marginBorder(1,1);
                    continue;
                }

                String response = sendMessage(userMessage);

                insertControlChars('n', 1);
                out.print(alignment(getDefaultTextAlignment()) + getColor(204) + "ChatGPT:");
                slowMotionText(50, getDefaultTextAlignment(),false,
                        getColor(sysMainColor) + response,"");
            }
        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error: " + e.getMessage(), sysLayoutColor, getDefaultTextAlignment(), 0, out::println);
        }
    }
}