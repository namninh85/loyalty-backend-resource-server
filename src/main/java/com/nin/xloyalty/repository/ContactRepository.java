package com.nin.xloyalty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nin.xloyalty.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
	Contact findByCustomerIdAndEmail(long customerId, String email);
	Contact findByCustomerId(Long Id);
}
