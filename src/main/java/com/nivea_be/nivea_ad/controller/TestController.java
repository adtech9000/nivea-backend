package com.nivea_be.nivea_ad.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping
    private String checkHealth(){
        return "Health Check OK!!!";
    }
}
