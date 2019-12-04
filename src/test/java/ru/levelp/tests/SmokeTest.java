package ru.levelp.tests;

import org.junit.*;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SmokeTest {
    private EntityManagerFactory factory;
    private EntityManager manager;

    @Before
    public void setup() {
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();
    }

    @After
    public void cleanup() {
        if (manager != null) {
            manager.close();
        }
        if (factory != null) {
            factory.close();
        }
    }

    @Test
    public void testCreateAccount() throws Exception {
        Account account = new Account();
        account.setLogin("test");
        account.setEncryptedPassword("123");

        manager.getTransaction().begin();
        try {
            manager.persist(account);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        Assert.assertNotNull(manager.find(Account.class, account.getId()));
        Assert.assertNotNull(manager.find(Account.class, account.getId()));
    }

    @Test
    public void queryAccount() {
        Account account = new Account();
        account.setLogin("test");
        account.setEncryptedPassword("123");

        manager.getTransaction().begin();
        try {
            manager.persist(account);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        Account found = manager.createQuery("from Account where login = :p", Account.class)
                .setParameter("p", "test")
                .getSingleResult();

        assertEquals(account.getId(), found.getId());
        assertEquals("123", found.getEncryptedPassword());
    }

    @Test
    public void queryTransactions() {
        Account account = new Account();
        account.setLogin("test");
        account.setEncryptedPassword("123");

        Account receiver = new Account();
        receiver.setLogin("another");
        receiver.setEncryptedPassword("456");

        Transaction tx = new Transaction();
        tx.setAmount(10);
        tx.setOrigin(account);
        tx.setReceiver(receiver);
        tx.setTime(new Date());

        manager.getTransaction().begin();
        try {
            manager.persist(account);
            manager.persist(receiver);
            manager.persist(tx);

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        List<Transaction> found = manager.createQuery(
                "from Transaction where origin.login = :p", Transaction.class)
                .setParameter("p", "test")
                .getResultList();

        assertEquals(1, found.size());
        assertEquals(tx.getId(), found.get(0).getId());
    }

    @Test
    public void criteriaBuilder() {
        Account account = new Account();
        account.setLogin("test");
        account.setEncryptedPassword("123");

        manager.getTransaction().begin();
        try {
            manager.persist(account);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Account> query = builder.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        query.select(root);

        Path<Object> login = root.get("login");
        query.where(
                builder.equal(login, "test")
        );

        Account found = manager.createQuery(query).getSingleResult();

        assertEquals(account.getId(), found.getId());
    }
}
