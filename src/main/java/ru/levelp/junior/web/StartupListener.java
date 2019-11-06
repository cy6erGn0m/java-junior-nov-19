package ru.levelp.junior.web;

import ru.levelp.junior.dao.AccountsDAO;
import ru.levelp.junior.dao.TransactionsDAO;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Date;
import java.util.Random;

@WebListener
public class StartupListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");

        Account testAccount;
        Account secondAccount;
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        AccountsDAO dao = new AccountsDAO(manager);
        TransactionsDAO tx = new TransactionsDAO(manager);
        try {
            testAccount = dao.findByLogin("test");
            secondAccount = dao.findByLogin("second");
        } catch (NoResultException notFound) {
            testAccount = new Account("test", "123");
            secondAccount = new Account("second", "333");

            dao.create(testAccount);
            dao.create(secondAccount);

            for (int i = 0; i < 10; ++i) {
                tx.create(new Transaction(new Date(), new Random().nextDouble() * 100, testAccount, secondAccount));
            }

            manager.getTransaction().commit();
        } finally {
            manager.close();
        }

        event.getServletContext().setAttribute("factory", factory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        EntityManagerFactory factory = getFactory(event.getServletContext());

        if (factory != null) {
            factory.close();
        }
    }

    public static EntityManagerFactory getFactory(ServletContext context) {
        return (EntityManagerFactory) context.getAttribute("factory");
    }
}
