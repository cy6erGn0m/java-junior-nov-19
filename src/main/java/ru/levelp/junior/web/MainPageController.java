package ru.levelp.junior.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    @GetMapping(path = "/")
    public String index() {
        return "mainPage";
    }
}
