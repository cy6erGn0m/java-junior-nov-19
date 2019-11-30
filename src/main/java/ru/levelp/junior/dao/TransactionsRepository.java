package ru.levelp.junior.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;

@Repository
public interface TransactionsRepository extends PagingAndSortingRepository<Transaction, Long> {
//    @Query("select a from Transaction a where a.origin = :account or a.receiver = :account")
//    Page<Transaction> findByAccount(Account account,
//                                             Sort sort);

    @Query("select a from Transaction a where a.origin = :account or a.receiver = :account")
    Page<Transaction> findByAccount(@Param("account") Account account,
                                             Pageable pageable);
}
