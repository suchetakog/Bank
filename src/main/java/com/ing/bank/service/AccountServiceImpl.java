package com.ing.bank.service;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.bank.dto.ResponseAccountDto;
import com.ing.bank.entity.Account;
import com.ing.bank.exception.CommonException;
import com.ing.bank.repository.AccountRepository;
import com.ing.bank.util.ExceptionConstants;

@Service
public class AccountServiceImpl implements AccountService, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	transient AccountRepository accountRepository;

	@Override
	public ResponseAccountDto accountSummary(Long customerId) {
		Account account = accountRepository.findByCustomerId(customerId);
		if (account == null) {
			throw new CommonException(ExceptionConstants.ACCOUNT_NOT_FOUND);
		}
		ResponseAccountDto responseAccountDto = new ResponseAccountDto();
		BeanUtils.copyProperties(account, responseAccountDto, "customerId","accountId");
		return responseAccountDto;
	}

}
