package com.ing.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.bank.dto.ListBeneficiaryDto;
import com.ing.bank.dto.RequestBeneficiaryDto;
import com.ing.bank.dto.ResponseBeneficiaryDto;
import com.ing.bank.service.BeneficiaryService;

@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/customer")

public class BeneficiaryController {

	@Autowired
	BeneficiaryService beneficiaryService;

	@PostMapping("/beneficiary")
	public ResponseEntity<ResponseBeneficiaryDto> addBeneficiary(
			@RequestBody RequestBeneficiaryDto requestBeneficiaryDto) {
		ResponseBeneficiaryDto responseBeneficiaryDto = beneficiaryService.addBeneficiary(requestBeneficiaryDto);
		return new ResponseEntity<>(responseBeneficiaryDto, HttpStatus.CREATED);
	}	

	@GetMapping("/beneficiary/{customerId}")
	public ResponseEntity<List<ListBeneficiaryDto>> viewBeneficiaries(@PathVariable("customerId") Long customerId) {
		List<ListBeneficiaryDto> listBeneficiaryDto = beneficiaryService.viewBeneficiaries(customerId);
		return new ResponseEntity<>(listBeneficiaryDto, HttpStatus.FOUND);
	}
}
