package com.code4piter.blueskythinking.repository;

import com.code4piter.blueskythinking.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
}
