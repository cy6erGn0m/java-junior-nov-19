package ru.levelp.junior.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    @Autowired
    private AccountsRepository allAccounts;

    @GetMapping(path = "/")
    public String index() {
        return "mainPage";
    }
}
