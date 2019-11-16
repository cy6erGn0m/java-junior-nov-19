package ru.levelp.junior.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.junior.entities.Account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AccountsDAO {
    @PersistenceContext
    private EntityManager manager;

    public AccountsDAO() {
    }

    public AccountsDAO(EntityManager manager) {
        this.manager = manager;
    }

    @Transactional
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
