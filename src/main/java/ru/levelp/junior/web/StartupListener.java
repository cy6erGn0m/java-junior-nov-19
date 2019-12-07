package ru.levelp.junior.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.junior.dao.AccountsDAO;
import ru.levelp.junior.dao.TransactionsDAO;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;

import java.util.Date;
import java.util.Random;

@Component
public class StartupListener {

    @Autowired
    private AccountsDAO dao;

    @Autowired
    private TransactionsDAO tx;

    @Autowired
    private PasswordEncoder encoder;

    @EventListener
    @Transactional
    public void handleContextRefreshEvent(ContextRefreshedEvent ctxStartEvt) {
        Account testAccount;
        Account secondAccount;

        try {
            testAccount = dao.findByLogin("test");
            secondAccount = dao.findByLogin("second");
        } catch (EmptyResultDataAccessException notFound) {
            testAccount = new Account("test", encoder.encode("123"));
            secondAccount = new Account("second", encoder.encode("333"));

            dao.create(testAccount);
            dao.create(secondAccount);

            for (int i = 0; i < 10; ++i) {
                tx.create(new Transaction(new Date(), new Random().nextDouble() * 100, testAccount, secondAccount));
            }
        }
    }
}
