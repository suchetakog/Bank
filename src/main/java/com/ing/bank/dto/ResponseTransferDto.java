package com.ing.bank.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseTransferDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long transferredId;
	private String message;

}
