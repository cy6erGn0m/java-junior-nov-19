package ru.levelp.junior.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.levelp.junior.entities.Account;

import javax.persistence.EntityManager;

@Repository
public class AccountsDAO {
    private final EntityManager manager;

    @Autowired
    public AccountsDAO(EntityManager manager) {
        this.manager = manager;
    }

    public void create(Account account) {
        manager.persist(account);
    }

    public Account findById(int accountId) {
        return manager.find(Account.class, accountId);
    }

    public Account findByLogin(String login) {
        return manager.createQuery(
                "from Account where login = :p", Account.class)
                .setParameter("p", login)
                .getSingleResult();
    }

    public Account findByLoginAndPassword(String login, String password) {
        return manager.createQuery(
                "from Account where login = :login AND password = :password", Account.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .getSingleResult();
    }
}
