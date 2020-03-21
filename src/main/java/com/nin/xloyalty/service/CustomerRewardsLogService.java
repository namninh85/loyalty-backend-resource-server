package com.nin.xloyalty.service;

import com.nin.xloyalty.model.CustomerRewardsLog;
import com.nin.xloyalty.repository.CustomerRewardsLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerRewardsLogService {
    @Autowired
    private CustomerRewardsLogRepository customerRewardsLogRepository;
    @Autowired
    public CustomerRewardsLogService(CustomerRewardsLogRepository customerRewardsLogRepository) {
        this.customerRewardsLogRepository = customerRewardsLogRepository;
    }

    public List<Object[]> findCustomerRewardsLogByCustomerId(Long Id){
        return customerRewardsLogRepository.findCustomerRewardsLogByCustomerId(Id);
    }
    
    public List<CustomerRewardsLog> findAllCustomerRewardsLogByCustomerIdActive(Long customerId){
        return customerRewardsLogRepository.findByCustomerIdAndIsActive(customerId, true);
    }


    public void createCustomerRewardsLog(CustomerRewardsLog customerRewardsLog) {
        customerRewardsLogRepository.save(customerRewardsLog);
        return;
    }
}
