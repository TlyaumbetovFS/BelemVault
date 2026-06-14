package com.belemvault.service;

import com.belemvault.model.User;
import com.belemvault.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findOrCreate(Long chatId) {
        return userRepository.findByChatId(chatId)
                .orElseGet(() -> {
                    var newUser = new User();
                    newUser.setChatId(chatId);
                    return userRepository.save(newUser);
                });
    }

    @Transactional(readOnly = true)
    public Optional<User> find(Long chatId) {
        return userRepository.findByChatId(chatId);
    }
}
