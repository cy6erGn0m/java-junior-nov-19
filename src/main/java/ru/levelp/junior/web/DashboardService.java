package ru.levelp.junior.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.junior.dao.AccountsDAO;
import ru.levelp.junior.dao.TransactionsDAO;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;

import javax.persistence.NoResultException;
import java.util.List;

@Service
public class DashboardService {
    @Autowired
    private AccountsDAO accounts;

    @Autowired
    private TransactionsDAO transactions;

    @Transactional
    public List<Transaction> getTransactions(int accountId) {
        Account account = accounts.findById(accountId);
        if (account == null) {
            throw new NoResultException("Account with id " + accountId + " not found");
        }

        return transactions.findByAccount(account);
    }
}
