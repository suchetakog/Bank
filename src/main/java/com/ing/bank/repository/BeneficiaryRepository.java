package com.ing.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ing.bank.entity.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

	List<Beneficiary> findByUserAccountNumber(String accountNumber);

	

	Beneficiary findByUserAccountNumberAndBeneficiaryAccountNumber(String accountNumber , String accountNumber2);

}
