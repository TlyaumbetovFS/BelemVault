package com.belemvault.controller;

import com.belemvault.service.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandController {
    private final CommandService commandService;

    public String process(String text) {
        return commandService.handle(text);
    }
}
