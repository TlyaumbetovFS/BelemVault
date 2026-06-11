package com.belemvault.service;

public class CommandService {
    public String handle(String text) {
        if (text.equalsIgnoreCase("/start")) {
            return "Привет!";
        } else if (text.startsWith("/")) {
            return "Неизвестная команда: " + text;
        }
        return text;
    }
}
