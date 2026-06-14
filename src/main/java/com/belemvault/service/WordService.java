package com.belemvault.service;

import com.belemvault.model.Word;
import com.belemvault.repository.UserRepository;
import com.belemvault.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordService {
    private final WordRepository wordRepository;
    private final UserService userService;

    @Transactional
    public Word addWord(Long chatId, String word, String translation) {
        var user = userService.findOrCreate(chatId);

        var newWord = new Word();
        newWord.setWord(word);
        newWord.setTranslation(translation);
        newWord.setUser(user);

        return wordRepository.save(newWord);
    }

    @Transactional(readOnly = true)
    public List<Word> getWords(Long chatId) {
        return userService.find(chatId)
                .map(user -> wordRepository.findByUserId(user.getId()))
                .orElse(List.of());
    }
}
