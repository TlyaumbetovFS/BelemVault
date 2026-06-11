package com.belemvault;

import com.belemvault.controller.CommandController;
import com.belemvault.service.CommandService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;

public class Main {
    public static void main(String[] args) {
        var token = System.getenv("BOT_TOKEN");
        var bot = new TelegramBot(token);

        var service = new CommandService();
        var controller = new CommandController(service);

        bot.setUpdatesListener(updates -> {
            for (var update : updates) {
                var message = update.message();
                if (message != null && message.text() != null) {
                    var text = message.text();
                    var chatId = (long)message.chat().id();
                    var output = controller.process(text);
                    bot.execute(new SendMessage(chatId, output));
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