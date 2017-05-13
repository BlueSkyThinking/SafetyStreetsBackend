package com.code4piter.blueskythinking.service;

import com.code4piter.blueskythinking.entity.Word;
import com.code4piter.blueskythinking.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordServiceImpl implements WordService {

    @Autowired
    private WordRepository repository;

    @Override
    public List<Word> getAll() {
        return repository.findAll();
    }
}
