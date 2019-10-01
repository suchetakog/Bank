package com.ing.bank.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ing.bank.dto.TransactionDetail;
import com.ing.bank.entity.Account;
import com.ing.bank.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("select t from Transaction t where t.account.accountId=?1 ORDER BY transactionId DESC")
	List<Transaction> findByAccount(Long accountId, Pageable pageable);

	@Query("select t from Transaction t where t.account.accountId = :accountId AND t.transferredDate BETWEEN :fromDate AND :toDate ")
	List<Transaction> findByAccountAndTransferredDate(Long accountId, LocalDate fromDate, LocalDate toDate);

	@Query("select t from Transaction t where transferredDate between :fromDate and :toDate")
	List<TransactionDetail> findByTransferredAmount(LocalDate fromDate, LocalDate toDate);
    
	//@Query("select t from Transaction t where t.account.accountId = :accountId AND t.transferredDate BETWEEN :fromDate AND :toDate")
	List<Transaction> findByAccountAndTransferredDateBetween(Account account, LocalDate fromDate, LocalDate toDate);

}
