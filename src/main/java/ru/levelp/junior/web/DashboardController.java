package ru.levelp.junior.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.levelp.junior.entities.Transaction;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    private DashboardService dashboard;

    @GetMapping(path = "/dashboard")
    public String dashboard(HttpSession session, ModelMap model) {
        try {
            int accountId = (int) session.getAttribute("accountId");
            List<Transaction> transactions = dashboard.getTransactions(accountId);

            model.addAttribute("transactions", transactions);

            return "dashboard";
        } catch (Exception e) {
            return "mainPage";
        }
    }
}
