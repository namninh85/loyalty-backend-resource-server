package com.nin.xloyalty.repository;

import com.nin.xloyalty.model.CustomerHasVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerHasVoucherRepository extends JpaRepository<CustomerHasVoucher,Long> {
    @Query(value = "SELECT COUNT(customer_has_voucher_id) as sl FROM customer_has_voucher " +
            "WHERE customer_id=:Id GROUP BY customer_id",nativeQuery = true)
    Integer countVoucherByCustomerId(@Param("Id") Long Id);
}
