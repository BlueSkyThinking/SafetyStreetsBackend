package com.code4piter.blueskythinking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void testing(){
        System.out.println("Hello!");
    }
}
