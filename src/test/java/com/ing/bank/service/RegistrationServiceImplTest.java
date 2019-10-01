package com.ing.bank.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.ing.bank.dto.RegisterRequestDto;
import com.ing.bank.dto.RegisterResponseDto;
import com.ing.bank.entity.Account;
import com.ing.bank.entity.Customer;
import com.ing.bank.repository.AccountRepository;
import com.ing.bank.repository.RegisterRepository;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceImplTest {

	@Mock
	RegisterRepository registerRepository;

	@Mock
	AccountRepository accountRepository;

	@InjectMocks
	RegisterServiceImpl registerServiceImpl;
	
	RegisterRequestDto registerRequestDto;

	Customer customer;

	Account account;

	@Before
	public void setup() {
		registerRequestDto = new RegisterRequestDto();
		registerRequestDto.setCustomerName("sucheta");
		registerRequestDto.setMobileNumber(9988776655L);
		registerRequestDto.setGender("female");
		registerRequestDto.setEmail("sucheta@gmail.com");
		registerRequestDto.setDateOfBirth("1993-01-01");

		customer = new Customer();
		customer.setCustomerId(1l);
		customer.setCustomerName("sucheta");
		customer.setAge(25);
		customer.setGender("female");
		customer.setMobileNumber(9988776655L);
		customer.setPassword("KoMcw");

		account = new Account();
		account.setAccountId(1L);
		account.setAccountNumber("36429173934033");
		account.setAccountType("savings");
		account.setBalance(10000D);
		account.setCustomerId(1L);
	}

	@Test
	public void testRegister() {
		Mockito.when(registerRepository.findByEmail(Mockito.anyString())).thenReturn(null);
		Mockito.when(registerRepository.save(Mockito.any())).thenReturn(customer);
		Mockito.when(accountRepository.save(Mockito.any())).thenReturn(account);
		RegisterResponseDto registerResponseDto = registerServiceImpl.register(registerRequestDto);
		Assert.assertEquals("36429173934033", registerResponseDto.getAccountNumber());

	}
}
