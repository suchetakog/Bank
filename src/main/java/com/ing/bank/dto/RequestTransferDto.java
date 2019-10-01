package com.ing.bank.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestTransferDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fromAccount;
	private String toAccount;
	private double transferredAmount;

}
