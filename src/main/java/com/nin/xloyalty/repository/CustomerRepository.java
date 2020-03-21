package com.nin.xloyalty.repository;

import com.nin.xloyalty.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Customer findByCustomerIdAndEmail(long customerId, String email);
	Customer findByCustomerId(Long Id);
}
