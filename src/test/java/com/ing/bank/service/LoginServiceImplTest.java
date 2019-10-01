package com.ing.bank.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.ing.bank.dto.RequestLoginDto;
import com.ing.bank.dto.ResponseLoginDto;
import com.ing.bank.entity.Customer;
import com.ing.bank.repository.RegisterRepository;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceImplTest {

	@Mock
	RegisterRepository registerRepository;

	@InjectMocks
	LoginServiceImpl loginServiceImpl;

	RequestLoginDto requestLoginDto;

	Customer customer;

	@Before
	public void setup() {
		requestLoginDto = new RequestLoginDto();
		requestLoginDto.setCustomerName("sucheta");
		requestLoginDto.setPassword("sucheta");
		customer = new Customer();
		customer.setCustomerId(1L);
		customer.setGender("female");
	}

	@Test
	public void testLogin() {
		Mockito.when(registerRepository.findByCustomerNameAndPassword(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(customer);
		ResponseLoginDto responseLoginDto = loginServiceImpl.login(requestLoginDto);
		Assert.assertEquals(Long.valueOf(1L), responseLoginDto.getCustomerId());
	}

}
