package ru.levelp.junior.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.levelp.junior.dao.AccountsDAO;
import ru.levelp.junior.dao.AccountsRepository;
import ru.levelp.junior.dao.TransactionsDAO;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;

import java.util.List;
import java.util.Optional;

@Service
public class DashboardService {
    @Autowired
    private AccountsRepository dao;

    @Autowired
    private TransactionsDAO tx;

    public List<Transaction> getTransactions(int accountId) {
        return dao.findById(accountId).map(account -> tx.findByAccount(account)).get();
    }
}
