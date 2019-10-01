package com.ing.bank.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ing.bank.dto.RequestTransferDto;
import com.ing.bank.dto.ResponseTransferDto;
import com.ing.bank.dto.TransactionDetail;
import com.ing.bank.dto.TransactionHistoryDto;
import com.ing.bank.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/customer")
public class TransactionController {
	

	@Autowired
	TransactionService transactionService;

	@PostMapping("/transfer")
	public ResponseEntity<ResponseTransferDto> transfer(@RequestBody RequestTransferDto requestTransferDto) {
		log.info("inside fund transafer");
		ResponseTransferDto responseTransferDto = transactionService.transfer(requestTransferDto);
		return new ResponseEntity<>(responseTransferDto, HttpStatus.OK);
	}

	@GetMapping("/transactions/{accountId}")
	public ResponseEntity<List<TransactionHistoryDto>> transactionHistory(@PathVariable("accountId") Long accountId) {
		log.info("inside fund transfer statement");
		List<TransactionHistoryDto> transactionHistoryDto = transactionService.transactionHistory(accountId);
		return new ResponseEntity<>(transactionHistoryDto, HttpStatus.OK);
	}

	@GetMapping("/transactionSummary/{customerId}")
	public ResponseEntity<List<TransactionHistoryDto>> weekHistory(@PathVariable("customerId") Long customerId) {
		List<TransactionHistoryDto> listTransactionHistoryDto = transactionService.weekHistory(customerId);
		return new ResponseEntity<>(listTransactionHistoryDto, HttpStatus.FOUND);
	}

	@GetMapping("/transactionHistory")
	public ResponseEntity<List<TransactionDetail>> history(@RequestParam("weeks") int weeks) {
		List<TransactionDetail> transactionDetail = transactionService.findByTransferredAmount( weeks);
		return new ResponseEntity<>(transactionDetail, HttpStatus.FOUND);
	}
	
	@GetMapping("/historyByMonth/{customerId}")
	public ResponseEntity<List<TransactionHistoryDto>> monthHistory(@PathVariable("customerId") Long customerId,@RequestParam("year") int year,@RequestParam("monthName") String monthName){
		List<TransactionHistoryDto> monthHistoryResponse = transactionService.monthHistory(customerId,year,monthName);
		return new ResponseEntity<>(monthHistoryResponse, HttpStatus.OK);
	}

}
