package com.belemvault;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;

public class Main {
    public static void main(String[] args) {
        var token = System.getenv("BOT_TOKEN");
        var bot = new TelegramBot(token);

        bot.setUpdatesListener(updates -> {
            for (var update : updates) {
                var message = update.message();
                if (message != null && message.text() != null) {
                    var text = message.text();
                    if (text.equalsIgnoreCase("/start")) {
                        bot.execute(new SendMessage((long)message.chat().id(), "Привет!"));
                    } else if (text.startsWith("/")) {
                        bot.execute(new SendMessage((long)message.chat().id(), "Неизвестная команда: " + text));
                    } else {
                        bot.execute(new SendMessage((long)message.chat().id(), text));
                    }
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