package com.code4piter.blueskythinking.controller;

import com.code4piter.blueskythinking.entity.Word;
import com.code4piter.blueskythinking.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/words")
public class WordController {

    @Autowired
    private WordService service;

    @RequestMapping(value = "/getWords", method = RequestMethod.GET)
    public List<Word> getAll() {
        return service.getAll();
    }
}
