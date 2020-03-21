package com.nin.xloyalty.repository;

import com.nin.xloyalty.model.CustomerRewardsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRewardsLogRepository extends JpaRepository<CustomerRewardsLog,Long> {
    @Query(value = "SELECT c.loyalty_program_id,COUNT(c.customer_rewards_log_id) AS uAvailable" +
            " FROM customer_rewards_log c WHERE  c.customer_id =:Id " +
            "GROUP BY c.loyalty_program_id",nativeQuery = true)
    List<Object[]> findCustomerRewardsLogByCustomerId(@Param("Id") Long Id);
    List<CustomerRewardsLog> findByCustomerIdAndIsActive(long customerId, boolean isActive);
    
}
