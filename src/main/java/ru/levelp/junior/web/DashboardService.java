package ru.levelp.junior.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.levelp.junior.dao.AccountsRepository;
import ru.levelp.junior.dao.TransactionsDAO;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;

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
}
