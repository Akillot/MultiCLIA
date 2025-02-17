package core.bots;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.insertControlChars;
import static core.configs.TextConfigs.message;
import static java.lang.System.out;

public class TelegramBot extends TelegramLongPollingBot {

    @Getter
    private final String botToken = System.getenv("TELEGRAM_BOT_TOKEN");

    public TelegramBot() {
        if (botToken == null || botToken.isEmpty()) {
            throw new IllegalStateException("TELEGRAM_BOT_TOKEN is not set!");
        }
    }

    @Override
    public String getBotUsername() {
        return "MultiCliam_bot";
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String userMessage = update.getMessage().getText();

            sendMessage(chatId, "You wrote: " + userMessage);
        }
    }

    public synchronized void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            insertControlChars('n', 1);
            message("Error: " + e.getMessage(), sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }
}
