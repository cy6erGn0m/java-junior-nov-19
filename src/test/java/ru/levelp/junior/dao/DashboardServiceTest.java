package ru.levelp.junior.dao;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;
import ru.levelp.junior.web.AccountsRepository;
import ru.levelp.junior.web.DashboardService;
import ru.levelp.tests.TestConfig;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DashboardServiceTest {
    @Autowired
    private DashboardService dashboard;

    @Autowired
    private AccountsRepository accounts;

    @Autowired
    private TransactionsDAO transactions;

    private int accountId;

    @Before
    @Transactional
    public void setup() {
        Account testAccount = new Account("test", "test");
        accounts.save(testAccount);

        Account testAccount2 = new Account("test2", "test");
        accounts.save(testAccount2);

        Transaction tx = new Transaction(new Date(), 10, testAccount, testAccount2);
        transactions.create(tx);

        accountId = testAccount.getId();
    }

    @Test
    public void testGetTransactions() {
        List<Transaction> transactions = dashboard.getTransactions(accountId);

        assertEquals(1, transactions.size());
    }
}
