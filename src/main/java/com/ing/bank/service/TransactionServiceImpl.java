package com.ing.bank.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ing.bank.dto.RequestTransferDto;
import com.ing.bank.dto.ResponseTransferDto;
import com.ing.bank.dto.TransactionDetail;
import com.ing.bank.dto.TransactionHistoryDto;
import com.ing.bank.entity.Account;
import com.ing.bank.entity.Transaction;
import com.ing.bank.exception.CommonException;
import com.ing.bank.exception.NoTransactionRecordFoundException;
import com.ing.bank.repository.AccountRepository;
import com.ing.bank.repository.TransactionRepository;
import com.ing.bank.util.ExceptionConstants;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

	

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	AccountRepository accountRepository;

	@Transactional
	public ResponseTransferDto transfer(RequestTransferDto requestTransferDto) {
		log.info("inside transaction service");

		ResponseTransferDto responseTransferDto = new ResponseTransferDto();

		Transaction debitTransaction = new Transaction();
		Transaction creditTransaction = new Transaction();

		Account fromAccount = accountRepository.findByAccountNumber(requestTransferDto.getFromAccount());
		Account toAccount = accountRepository.findByAccountNumber(requestTransferDto.getToAccount());
		if (fromAccount.getBalance() < requestTransferDto.getTransferredAmount()) {
			throw new CommonException(ExceptionConstants.INVALID_AMOUNT);
		}
		debitTransaction.setAccountNumber(requestTransferDto.getFromAccount());
		debitTransaction.setTransactionType("debit");
		debitTransaction.setTransferredAmount(requestTransferDto.getTransferredAmount());
		debitTransaction.setTransferredDate(LocalDate.now());
		debitTransaction.setAccount(fromAccount);

		//creditTransaction.setDebitFrom(requestTransferDto.getFromAccount());
		creditTransaction.setAccountNumber(requestTransferDto.getToAccount());
		creditTransaction.setTransactionType("credit");
		creditTransaction.setTransferredAmount(requestTransferDto.getTransferredAmount());
		creditTransaction.setTransferredDate(LocalDate.now());
		creditTransaction.setAccount(toAccount);

		double remainingBalance = fromAccount.getBalance() - requestTransferDto.getTransferredAmount();
		double updatedBalance = toAccount.getBalance() + requestTransferDto.getTransferredAmount();

		fromAccount.setBalance(remainingBalance);
		toAccount.setBalance(updatedBalance);

		accountRepository.save(fromAccount);

		Transaction transaction = transactionRepository.save(debitTransaction);
		if (transaction.getTransactionId() == null) {
			throw new CommonException(ExceptionConstants.TRANSACTION_FAILED);
		}
		accountRepository.save(toAccount);
		transactionRepository.save(creditTransaction);

		responseTransferDto.setTransferredId(transaction.getTransactionId());
		responseTransferDto.setMessage("Amount Transferred successfully..");
		return responseTransferDto;

	}

	public List<TransactionHistoryDto> transactionHistory(Long accountId) {

		List<TransactionHistoryDto> listTransactionHistoryDto = new ArrayList<>();
		Pageable pageable = PageRequest.of(0, 10);
		List<Transaction> transaction = transactionRepository.findByAccount(accountId, pageable);
		if (transaction != null) {
			transaction.stream().forEach(a -> {
				TransactionHistoryDto transactionHistoryDto = new TransactionHistoryDto();
				BeanUtils.copyProperties(a, transactionHistoryDto);
				listTransactionHistoryDto.add(transactionHistoryDto);
				

			});
			return listTransactionHistoryDto;
		} else {
			throw new NoTransactionRecordFoundException();
		}
	}

	@Override
	public List<TransactionHistoryDto> weekHistory(Long customerId) {
		Account account = accountRepository.findByCustomerId(customerId);
		Long accountId = account.getAccountId();
		LocalDate fromDate = LocalDate.now().minusDays(7);
		LocalDate toDate = LocalDate.now();
		List<TransactionHistoryDto> weekHistoryResponse = new ArrayList<>();

		List<Transaction> listTransaction = transactionRepository.findByAccountAndTransferredDate(accountId,
				fromDate, toDate);
		listTransaction.stream().forEach(transaction -> {
			TransactionHistoryDto transactionHistoryDto = new TransactionHistoryDto();
			BeanUtils.copyProperties(transaction, transactionHistoryDto, "monthName","year","account");
			weekHistoryResponse.add(transactionHistoryDto);
		});
		if (weekHistoryResponse.isEmpty()) {
			throw new CommonException(ExceptionConstants.WEEK_HISTORY_NOT_FOUND);
		}
		return weekHistoryResponse;
	}

	@Override
	public List<TransactionDetail> findByTransferredAmount(int weeks) {
		LocalDate fromDate = LocalDate.now().minusWeeks(weeks);
		LocalDate toDate = LocalDate.now();

		return transactionRepository.findByTransferredAmount(fromDate, toDate);

	}

	public List<TransactionHistoryDto> monthHistory(Long customerId, int year, String monthName) {
		Account account = accountRepository.findByCustomerId(customerId);

		List<TransactionHistoryDto> monthHistoryResponse = new ArrayList<>();
		Month mon = Month.valueOf(monthName);
		YearMonth yearMonth = YearMonth.of(year, mon);
		LocalDate fromDate = yearMonth.atDay(1);
		LocalDate toDate = yearMonth.atEndOfMonth();

		List<Transaction> listTransaction = transactionRepository.findByAccountAndTransferredDateBetween(account,
		fromDate, toDate);
		listTransaction.stream().forEach(transaction -> {
		TransactionHistoryDto transactionHistoryDto = new TransactionHistoryDto();
		BeanUtils.copyProperties(transaction, transactionHistoryDto, "monthName","year","account");
		monthHistoryResponse.add(transactionHistoryDto);
		});
		if (monthHistoryResponse.isEmpty()) {
		throw new CommonException(ExceptionConstants.MONTH_HISTORY_NOT_FOUND);
		}
		return monthHistoryResponse;

		}

	



}
