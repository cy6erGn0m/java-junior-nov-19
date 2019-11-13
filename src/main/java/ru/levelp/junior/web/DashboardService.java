package ru.levelp.junior.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.levelp.junior.dao.AccountsDAO;
import ru.levelp.junior.dao.TransactionsDAO;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;

import java.util.List;

@Service
public class DashboardService {
    @Autowired
    private AccountsDAO dao;

    @Autowired
    private TransactionsDAO tx;

    public List<Transaction> getTransactions(int accountId) {
        Account found = dao.findById(accountId);
        return tx.findByAccount(found);
    }
}