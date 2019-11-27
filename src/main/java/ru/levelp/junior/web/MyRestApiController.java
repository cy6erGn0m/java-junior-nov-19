package ru.levelp.junior.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.levelp.junior.dao.AccountsRepository;
import ru.levelp.junior.dao.TransactionsDAO;
import ru.levelp.junior.entities.Transaction;

import java.util.Arrays;
import java.util.List;

@RestController
public class MyRestApiController {
    @Autowired
    private TransactionsDAO transactions;

    @Autowired
    private AccountsRepository accounts;

    @GetMapping("/api/transactions/find")
    public List<Transaction> findByAccount(@RequestParam int accountId) {
        return transactions.findByAccount(accounts.findById(accountId).get());
    }

    @GetMapping("/api/example")
    public MyRestResponse findExample() {
        MyRestResponse response = new MyRestResponse();

        response.setName("zzz");
        response.setList(Arrays.asList("1", "2", "3"));

        return response;
    }
}
