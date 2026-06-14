package com.belemvault;

import com.belemvault.controller.CommandController;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelegramBotRunner implements CommandLineRunner {
    private final CommandController commandController;

    @Value("${bot.token}")
    private String token;

    @Override
    public void run(String... args) throws Exception {
        var bot = new TelegramBot(token);

        bot.setUpdatesListener(updates -> {
            for (var update : updates) {
                var message = update.message();
                if (message != null && message.text() != null) {
                    var text = message.text();
                    var chatId = (long)message.chat().id();
                    var output = commandController.process(chatId, text);
                    bot.execute(new SendMessage(chatId, output).parseMode(ParseMode.HTML));
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                System.err.println(e.response().errorCode() + " " + e.response().description());
            } else {
                e.printStackTrace();
            }
        });
    }
}
