package ru.levelp.junior.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.levelp.junior.dao.AccountsDAO;
import ru.levelp.junior.dao.TransactionsDAO;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.Random;

@Controller
public class StartupListener {

    @Autowired
    private AccountsDAO dao;

    @Autowired
    private TransactionsDAO tx;

    @GetMapping(path = "/init")
    public String handleContextRefreshEvent() {
        Account testAccount;
        Account secondAccount;

        try {
            dao.findByLogin("test");
            dao.findByLogin("second");
        } catch (NoResultException notFound) {
            testAccount = new Account("test", "123");
            secondAccount = new Account("second", "333");

            dao.create(testAccount);
            dao.create(secondAccount);

            for (int i = 0; i < 10; ++i) {
                tx.create(new Transaction(new Date(), new Random().nextDouble() * 100, testAccount, secondAccount));
            }
        }

        return "redirect:/";
    }
}
