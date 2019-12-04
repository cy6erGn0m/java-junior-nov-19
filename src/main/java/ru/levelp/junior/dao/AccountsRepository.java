package ru.levelp.junior.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.levelp.junior.entities.Account;

import java.util.List;

@Repository
public interface AccountsRepository extends PagingAndSortingRepository<Account, Integer> {
    Account findByLoginAndEncryptedPassword(String login, String encryptedPassword);
    Account findByLogin(String login);

    @Query("select a from Account a where a.login = :login")
    List<Account> findByXxx(String login);
}
