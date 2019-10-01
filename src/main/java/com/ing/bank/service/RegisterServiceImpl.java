package com.ing.bank.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.bank.dto.RegisterRequestDto;
import com.ing.bank.dto.RegisterResponseDto;
import com.ing.bank.entity.Account;
import com.ing.bank.entity.Customer;
import com.ing.bank.exception.CommonException;
import com.ing.bank.repository.AccountRepository;
import com.ing.bank.repository.RegisterRepository;
import com.ing.bank.util.ExceptionConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

/**
 * 
 * @author Sucheta
 *
 */
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	RegisterRepository registerRepository;

	@Autowired
	AccountRepository accountRepository;
	Random rand;
	/*
	 * This method is used to register the user by providing valid details, while
	 * registering it will generate password for the customer. Once customer registered
	 * successfully it will generate account for the user.
	 * 
	 * @Param customerName, gender,dateOfBirth, mobileNumber,email
	 * 
	 * @return RegisterResponseDto is the return object which includes
	 * customerId,password,accountNumber,message
	 * 
	 */

	public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {

		log.info("inside register service");
		Customer register = new Customer();
		Customer checkRegisterForEmail = registerRepository.findByEmail(registerRequestDto.getEmail());
		if (checkRegisterForEmail != null) {
			throw new CommonException(ExceptionConstants.EXIST_EMAIL);
		}
		if (!validMobileNumber(registerRequestDto.getMobileNumber())) {
			throw new CommonException(ExceptionConstants.INVALID_MOBILE_NUMBER);
		}

		if (!validEmail(registerRequestDto.getEmail())) {
			throw new CommonException(ExceptionConstants.INVALID_EMAIL);
		}
		LocalDate todayDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String birthDay = registerRequestDto.getDateOfBirth();
		LocalDate dob = LocalDate.parse(birthDay, formatter);
		int age = Period.between(dob, todayDate).getYears();

		if (age < 18) {
			throw new CommonException(ExceptionConstants.INVALID_AGE);
		}
		register.setCustomerName(registerRequestDto.getCustomerName());
		register.setGender(registerRequestDto.getGender());
		register.setEmail(registerRequestDto.getEmail());
		register.setMobileNumber(registerRequestDto.getMobileNumber());
		register.setPassword(generatePassword());
		register.setDateOfBirth(dob);
		register.setAge(age);
		Customer responseRegister = registerRepository.save(register);
		RegisterResponseDto registerResponseDto = new RegisterResponseDto();

		if (responseRegister.getCustomerId() == null) {
			throw new CommonException(ExceptionConstants.REGISTRATION_FAILED);
		}
		Account account = new Account();
		account.setAccountType("savings");
		account.setBalance(50000);
		account.setAccountNumber(accountNumber());
		account.setCustomerId(responseRegister.getCustomerId());
		account.setCreatedDate(LocalDate.now());
		Account responseAccount = accountRepository.save(account);

		registerResponseDto.setCustomerId(responseRegister.getCustomerId());
		registerResponseDto.setAccountNumber(responseAccount.getAccountNumber());
		registerResponseDto.setPassword(responseRegister.getPassword());
		registerResponseDto.setMessage("Registered successfully..");
		return registerResponseDto;
	}

	public String accountNumber() {
		
		rand =new Random();
		String number = "";
		for (int i = 0; i < 14; i++) {
			int n = rand.nextInt(10) + 0;
			number += Integer.toString(n);
		}
		return number;

	}

	public boolean validMobileNumber(Long number) {
		String num = number.toString();
		Pattern p = Pattern.compile("^[89]\\d{9}$");
		Matcher m = p.matcher(num);
		return (m.find() && m.group().equals(num));

	}

	public boolean validEmail(String email) {
		Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(email);
		return (m.find() && m.group().equals(email));
	}

	public String generatePassword() {
		SecureRandom r = new SecureRandom();
		final String alphaCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String alpha = "abcdefghijklmnopqrstuvwxyz";
		final String numeric = "0123456789";
		final String specialChars = "!@#$%^&*_=+-/";
		int length = 5;
		String dic = alphaCaps + alpha + numeric + specialChars;
		String result = "";
		for (int i = 0; i < length; i++) {
			int index = r.nextInt(dic.length());
			result += dic.charAt(index);
		}

		return result;
	}

}
