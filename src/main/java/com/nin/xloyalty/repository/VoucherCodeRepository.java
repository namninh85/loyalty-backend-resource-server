package com.nin.xloyalty.repository;

import com.nin.xloyalty.model.VoucherCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface VoucherCodeRepository extends JpaRepository<VoucherCode,Long> {
    @Query(value = "SELECT c.voucher_code_id AS voucher_code_id FROM voucher_code c " +
            "WHERE c.voucher_id=:voucherId AND c.is_active='true' ORDER BY c.voucher_code_id DESC LIMIT 1",nativeQuery = true)
    Long findVoucherCodeByVoucherId(@Param("voucherId") Long voucherId);
}
