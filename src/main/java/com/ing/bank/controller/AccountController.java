package com.ing.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.bank.dto.ResponseAccountDto;
import com.ing.bank.service.AccountService;

@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/customer")
public class AccountController {
	@Autowired
	AccountService accountService;

	@GetMapping("summary/{customerId}")
	public ResponseEntity<ResponseAccountDto> accountSummary(@PathVariable("customerId") Long customerId) {
		ResponseAccountDto responseAccountDto = accountService.accountSummary(customerId);
		return new ResponseEntity<>(responseAccountDto, HttpStatus.FOUND);
	}

}
