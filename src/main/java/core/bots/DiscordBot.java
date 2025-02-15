package core.bots;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.insertControlChars;
import static core.configs.TextConfigs.message;
import static java.lang.System.out;

public class DiscordBot extends ListenerAdapter {
    public static void connectDiscordAPI() {
        String token = System.getenv("DISCORD_BOT_TOKEN");
        try {
            JDABuilder.createDefault(token)
                    .addEventListeners(new DiscordBot())
                    .build();
        } catch (Exception e) {
            insertControlChars('n',1);
            message(e.getMessage(), sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(),out::println);
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();
        if (!message.getAuthor().isBot()) {
            message("New " + getColor(sysMainColor) + "message" + getColor(sysLayoutColor) + " from "
                            + message.getAuthor().getName() + ": " + message.getContentDisplay(), sysLayoutColor,
                    getDefaultTextAlignment(),getDefaultDelay(),out::println);
        }
    }
}
