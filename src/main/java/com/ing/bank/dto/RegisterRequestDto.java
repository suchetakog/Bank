package com.ing.bank.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String customerName;
	private String gender;
	private String dateOfBirth;

	private Long mobileNumber;
	private String email;

}
