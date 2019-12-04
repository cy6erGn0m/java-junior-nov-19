package ru.levelp.junior.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.levelp.junior.entities.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class AccountsDAOTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private AccountsDAO dao;

    @Before
    public void setup() {
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();
        dao = new AccountsDAO(manager);
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
    public void create() {
        Account account = new Account();
        manager.getTransaction().begin();
        try {
            account.setLogin("test1");
            account.setEncryptedPassword("123");

            dao.create(account);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        assertNotNull(manager.find(Account.class, account.getId()));
    }

    @Test
    public void findByLogin() {
        Account account = new Account();
        manager.getTransaction().begin();
        try {
            account.setLogin("test1");
            account.setEncryptedPassword("123");

            manager.persist(account);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        Account found = dao.findByLogin("test1");
        assertNotNull(found);
        assertEquals(account.getId(), found.getId());

        try {
            dao.findByLogin("test2");
            fail("User test2 shouldn't be found");
        } catch (NoResultException expected) {
        }
    }

    @Test
    public void findByLoginAndPassword() {
        Account account = new Account();
        manager.getTransaction().begin();
        try {
            account.setLogin("test1");
            account.setEncryptedPassword("123");

            manager.persist(account);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        Account found = dao.findByLoginAndPassword("test1", "123");
        assertNotNull(found);
        assertEquals(account.getId(), found.getId());

        try {
            dao.findByLoginAndPassword("test1", "zzzz");
            fail("User test1 shouldn't be found");
        } catch (NoResultException expected) {
        }
    }
}