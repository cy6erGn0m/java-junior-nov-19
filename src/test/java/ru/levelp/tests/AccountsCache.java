package ru.levelp.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.levelp.junior.dao.AccountsDAO;
import ru.levelp.junior.entities.Account;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccountsCache {
    @Autowired
    private AccountsDAO users;

    private final ConcurrentHashMap<Integer, Account> cachedAccounts = new ConcurrentHashMap<>();

    public Account getAccount(int id) {
        Account account = cachedAccounts.get(id);
        if (account != null) {
            return account;
        }

        account = users.findById(id);
        cachedAccounts.put(id, account);
        return account;
    }

    public Account getAccount2(int id) {
//        cachedAccounts.putIfAbsent(id, users.findById(id));
//
//        if (!cachedAccounts.containsKey(id)) {
//            cachedAccounts.put(id, users.findById(id));
//        }

        return cachedAccounts.computeIfAbsent(id, key ->
                users.findById(id));
    }

    public void evictOld() {
        // some cleanup
    }
}
