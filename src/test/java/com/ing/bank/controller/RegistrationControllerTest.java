package com.ing.bank.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.bank.dto.RegisterRequestDto;
import com.ing.bank.service.RegisterService;

@RunWith(MockitoJUnitRunner.class)

public class RegistrationControllerTest {

	MockMvc mockMvc;
	
	@Mock
	RegisterService registerService;
	@InjectMocks
	RegistrationController registrationController;
	
	@Test
	public void TestRegister() throws Exception{
		mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
		RegisterRequestDto registerRequestDto=new RegisterRequestDto();
		registerRequestDto.setCustomerName("Sucheta");
		mockMvc.perform(MockMvcRequestBuilders.post("/customer/registration")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(asJsonString(registerRequestDto)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
