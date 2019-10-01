package com.ing.bank.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.bank.dto.RequestLoginDto;
import com.ing.bank.dto.ResponseLoginDto;
import com.ing.bank.entity.Customer;
import com.ing.bank.exception.CommonException;
import com.ing.bank.repository.RegisterRepository;
import com.ing.bank.util.ExceptionConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

	

	@Autowired
	RegisterRepository registerRepository;

	/*
	 * This method is used to login the user by providing valid credentials
	 * 
	 * @Param userName,password
	 * 
	 * @return ResponseLoginDto is the return object which includes userId,message
	 * 
	 */
	public ResponseLoginDto login(RequestLoginDto requestLoginDto) {
		log.info("inside login service");
		Customer register = registerRepository.findByCustomerNameAndPassword(requestLoginDto.getCustomerName(),
				requestLoginDto.getPassword());

		if (register == null) {
			throw new CommonException(ExceptionConstants.USER_NOT_FOUND);
		}
		ResponseLoginDto responseLoginDto = new ResponseLoginDto();
		responseLoginDto.setCustomerId(register.getCustomerId());
		responseLoginDto.setMessage("logged in successfully");
		
		return responseLoginDto;

	}

}
