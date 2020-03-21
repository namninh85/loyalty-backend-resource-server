package com.nin.xloyalty.service;

import com.nin.xloyalty.repository.ContactRepository;
import com.nin.xloyalty.model.Contact;
import com.nin.xloyalty.model.Customer;
import com.nin.xloyalty.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findByCustomerId(Long userId) {
        return customerRepository.findByCustomerId(userId);
    }

    public void updateVoucherAndPointCustomer(Customer customer) {
        customerRepository.save(customer);
        return;
    }

	public Contact findByContactId(Long userId) {
		return contactRepository.findByCustomerId(userId);
	}

	public Contact createOrUpdateContact(Contact aContact) {
		//TODO: Nin sync contact to salesforce (if email, phone are existed what will do?)
		return contactRepository.save(aContact);
	}

	public Customer createOrUpdateCustomer(Customer aCustomer) {
		return customerRepository.save(aCustomer);
	}
}
