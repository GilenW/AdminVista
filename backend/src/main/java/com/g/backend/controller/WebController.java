package com.g.backend.controller;

import com.g.backend.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    public WebController() {

    }
    @GetMapping("/hello")
    public String Hello() {
        return "Hello";
    }

    @GetMapping("/hello/p")
    public Result Hello1(){
        return Result.success(11);
    }
}

