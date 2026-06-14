package com.belemvault.repository;

import com.belemvault.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findByUserId(Long userId);
    Optional<Word> findByUserIdAndWord(Long userId, String word);
}
