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
    public boolean addWord(Long chatId, String word, String translation) {
        var user = userService.findOrCreate(chatId);

        if (wordRepository.findByUserIdAndWord(user.getId(), word).isPresent()) {
            return false;
        }

        var newWord = new Word();
        newWord.setWord(word);
        newWord.setTranslation(translation);
        newWord.setUser(user);

        wordRepository.save(newWord);
        return true;
    }

    @Transactional(readOnly = true)
    public List<Word> getWords(Long chatId) {
        return userService.find(chatId)
                .map(user -> wordRepository.findByUserId(user.getId()))
                .orElse(List.of());
    }

    @Transactional
    public boolean deleteWord(Long chatId, String word) {
        var user = userService.find(chatId);

        if (user.isEmpty()) {
            return false;
        }

        var found = wordRepository.findByUserIdAndWord(user.get().getId(), word);

        if (found.isEmpty()) {
            return false;
        }

        wordRepository.delete(found.get());
        return true;
    }
}
