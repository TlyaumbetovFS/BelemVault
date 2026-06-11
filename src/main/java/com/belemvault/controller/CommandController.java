package com.belemvault.controller;

import com.belemvault.service.CommandService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommandController {
    private final CommandService commandService;

    public String process(String text) {
        return commandService.handle(text);
    }
}
