package ru.levelp.junior.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TransactionsDAO {
    @PersistenceContext
    private EntityManager manager;

    public TransactionsDAO() {
    }

    public TransactionsDAO(EntityManager manager) {
        this.manager = manager;
    }

    @Transactional
    public void create(Transaction transaction) {
        if (transaction.getReceiver() == transaction.getOrigin()) {
            throw new IllegalArgumentException(
                    "Transaction receiver and origin accounts are the same.");
        }
        if (transaction.getAmount() <= 0) {
            throw new IllegalArgumentException("Transaction amount is negative");
        }

        manager.persist(transaction);
    }

    public List<Transaction> findByOrigin(Account origin) {
        return manager.createQuery(
                "from Transaction where origin.id = :p", Transaction.class
        ).setParameter("p", origin.getId())
                .getResultList();
    }

    public List<Transaction> findByReceiver(Account receiver) {
        return manager.createQuery(
                "from Transaction where receiver.id = :p", Transaction.class
        ).setParameter("p", receiver.getId())
                .getResultList();
    }

    public List<Transaction> findByAccount(Account account) {
        return manager.createQuery(
                "from Transaction where origin.id = :p or receiver.id = :p", Transaction.class
        ).setParameter("p", account.getId())
                .getResultList();
    }
}
