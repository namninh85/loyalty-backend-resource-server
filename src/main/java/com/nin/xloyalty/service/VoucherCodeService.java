package com.nin.xloyalty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nin.xloyalty.repository.VoucherCodeRepository;

@Service
public class VoucherCodeService {
    @Autowired
    private VoucherCodeRepository voucherCodeRepository;
    @Autowired
    public VoucherCodeService(VoucherCodeRepository voucherCodeRepository) {
        this.voucherCodeRepository = voucherCodeRepository;
    }

    public Long findVoucherCodeByVoucherId(Long voucherId){
        return voucherCodeRepository.findVoucherCodeByVoucherId(voucherId);
    }
}
