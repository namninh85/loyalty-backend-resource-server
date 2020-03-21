package com.nin.xloyalty.repository;

import com.nin.xloyalty.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Long> {
    Voucher findByVoucherId(Long Id);
}
