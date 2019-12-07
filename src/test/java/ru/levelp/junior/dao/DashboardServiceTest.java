package ru.levelp.junior.dao;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;
import ru.levelp.junior.web.DashboardService;
import ru.levelp.junior.web.security.SecurityConfig;
import ru.levelp.tests.TestConfig;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, SecurityConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DashboardServiceTest {
    @Autowired
    private DashboardService dashboard;

    @Autowired
    private AccountsDAO accounts;

    @Autowired
    private TransactionsDAO transactions;

    @Before
    @Transactional
    public void setup() {
        Account testAccount = new Account("test", "test");
        accounts.create(testAccount);

        Account testAccount2 = new Account("test2", "test");
        accounts.create(testAccount2);

        Transaction tx = new Transaction(new Date(), 10, testAccount, testAccount2);
        transactions.create(tx);
    }

    @Test
    public void testGetTransactions() {
        List<Transaction> transactions = dashboard.getTransactions("test");

        assertEquals(1, transactions.size());
    }

    @Test
    public void createTransactionNoSecurity() {
        try {
            dashboard.transferTo(accounts.findByLogin("test"), accounts.findByLogin("test2"), 12);
            fail();
        } catch (AuthenticationCredentialsNotFoundException expected) {
        }
    }

    @Test
    @WithAnonymousUser
    public void createTransactionAnonymous() {
        try {
            dashboard.transferTo(accounts.findByLogin("test"), accounts.findByLogin("test2"), 12);
            fail();
        } catch (AccessDeniedException expected) {
        }
    }

    @Test
    @WithMockUser(value = "user", roles = "USER")
    public void createTransactionByUser() {
        dashboard.transferTo(accounts.findByLogin("test"), accounts.findByLogin("test2"), 12);
    }
}
