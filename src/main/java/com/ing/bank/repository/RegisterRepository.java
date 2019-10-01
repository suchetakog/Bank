package com.ing.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.bank.entity.Customer;

@Repository
public interface RegisterRepository extends JpaRepository<Customer, Long> {

	Customer findByCustomerNameAndPassword(String customerName, String password);

	Customer findByEmail(String email);

	Customer findByCustomerId(Long customerId);

}
