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
import com.ing.bank.dto.RequestLoginDto;
import com.ing.bank.service.LoginServiceImpl;

@RunWith(MockitoJUnitRunner.class)

public class LoginControllerTest {

	MockMvc mockMvc;

	@InjectMocks
	LoginController loginController;

	@Mock
	LoginServiceImpl loginServiceImpl;

	@Test
	public void testLogin() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
		RequestLoginDto loginDTO = new RequestLoginDto();
		loginDTO.setCustomerName("sucheta");
		loginDTO.setPassword("wk0g7");
		mockMvc.perform(MockMvcRequestBuilders.post("/customer/login")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(asJsonString(loginDTO)))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
