package com.belemvault.controller;

import com.belemvault.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandController {
    private final WordService wordService;

    public String process(Long chatId, String text) {
        if (text.equalsIgnoreCase("/start")) {
            return "Привет!";
        } else if (text.equalsIgnoreCase("/list")) {
            return handleList(chatId);
        } else if (text.startsWith("/add ")) {
            return handleAdd(chatId, text);
        }
        return "Неизвестная команда";
    }

    private String handleList(Long chatId) {
        var words = wordService.getWords(chatId);

        if (words.isEmpty()) {
            return "Словарь пуст, добавьте слова через /add";
        }

        var sb = new StringBuilder();

        for (var word : words) {
            sb.append(word.getWord()).append(" - ").append("<tg-spoiler>").append(word.getTranslation()).append("</tg-spoiler>").append("\n");
        }

        return sb.toString();
    }

    private String handleAdd(Long chatId, String text) {
        var args = text.split(" ", 2);

        if (args.length != 2) {
            return "Использование: /add слово - перевод";
        }

        var words = args[1].split("-", 2);

        if (words.length != 2) {
            return "Использование: /add слово - перевод";
        }

        var word = words[0].trim();
        var translation = words[1].trim();

        if (word.isBlank() || translation.isBlank()) {
            return "Использование: /add слово - перевод";
        }

        wordService.addWord(chatId, word, translation);

        return "Слово: " + word + " успешно сохранено!";
    }
}
