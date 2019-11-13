package ru.levelp.junior.dao;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;
import ru.levelp.junior.web.AppConfig;
import ru.levelp.junior.web.DashboardService;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DashboardServiceTest {
    @Autowired
    private DashboardService dashboard;

    @Autowired
    private AccountsDAO accounts;

    @Autowired
    private TransactionsDAO transactions;

    @Autowired
    private EntityManager manager;

    private int accountId;

    @Before
    public void setup() {
        manager.getTransaction().begin();
        Account testAccount = new Account("test", "test");
        accounts.create(testAccount);

        Account testAccount2 = new Account("test2", "test");
        accounts.create(testAccount2);

        Transaction tx = new Transaction(new Date(), 10, testAccount, testAccount2);
        transactions.create(tx);
        manager.getTransaction().commit();

        accountId = testAccount.getId();
    }

    @Test
    public void testGetTransactions() {
        List<Transaction> transactions = dashboard.getTransactions(accountId);

        assertEquals(1, transactions.size());
    }
}
