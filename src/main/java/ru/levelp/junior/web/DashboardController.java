package ru.levelp.junior.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.levelp.junior.dao.AccountsRepository;
import ru.levelp.junior.entities.Transaction;

import java.security.Principal;
import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    private DashboardService dashboard;

    @Autowired
    private AccountsRepository accounts;

    @GetMapping(path = "/dashboard")
    public String dashboard(Principal principal, ModelMap model) {
        try {
            List<Transaction> transactions = dashboard.getTransactions(principal.getName());
            model.addAttribute("transactions", transactions);
            model.addAttribute("accountId", accounts.findByLogin(principal.getName()).getId());

            return "dashboard";
        } catch (Exception e) {
            return "mainPage";
        }
    }
}
