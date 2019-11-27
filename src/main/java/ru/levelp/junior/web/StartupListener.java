package ru.levelp.junior.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.junior.dao.AccountsDAO;
import ru.levelp.junior.dao.TransactionsDAO;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Date;
import java.util.Random;

@Component
public class StartupListener {

    @Autowired
    private AccountsDAO dao;

    @Autowired
    private TransactionsDAO tx;

    @EventListener
    @Transactional
    public void handleContextRefreshEvent(ContextRefreshedEvent ctxStartEvt) {
        Account testAccount;
        Account secondAccount;

        try {
            testAccount = dao.findByLogin("test");
            secondAccount = dao.findByLogin("second");
        } catch (NoResultException notFound) {
            testAccount = new Account("test", "1234");
            secondAccount = new Account("second", "3334");

            dao.create(testAccount);
            dao.create(secondAccount);

            for (int i = 0; i < 10; ++i) {
                tx.create(new Transaction(new Date(), new Random().nextDouble() * 100, testAccount, secondAccount));
            }
        }
    }
}
