package ru.levelp.junior.web;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.levelp.junior.entities.Account;

@Repository
@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
public interface AccountsRepository extends PagingAndSortingRepository<Account, Integer> {
    Account findByLogin(@Param("login") String login);
}
