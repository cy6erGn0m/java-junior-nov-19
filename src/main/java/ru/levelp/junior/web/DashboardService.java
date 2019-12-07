package ru.levelp.junior.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.junior.dao.AccountsRepository;
import ru.levelp.junior.dao.TransactionsDAO;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;

import java.util.Date;
import java.util.List;

@Service
public class DashboardService {
    @Autowired
    private AccountsRepository accounts;

    @Autowired
    private TransactionsDAO tx;

    public List<Transaction> getTransactions(String login) {
        Account account = accounts.findByLogin(login);
        return tx.findByAccount(account);
    }

    @Transactional
    @Secured("ROLE_USER")
    public void transferTo(Account sender, Account recipient, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount should be positive");
        }

        tx.create(new Transaction(new Date(), amount, sender, recipient));
    }
}
