package com.nin.xloyalty.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "voucher_code")
public class VoucherCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voucherCodeId;
    private Long voucherId;
    private String voucherCode;
    private Boolean isActive;
    private Boolean isDeleted;

    
    public VoucherCode(){}

    public VoucherCode(Long voucherCodeId, Long voucherId, String voucherCode, Boolean isActive, Boolean isDeleted) {
        this.voucherCodeId = voucherCodeId;
        this.voucherId = voucherId;
        this.voucherCode = voucherCode;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        
    }

    public Long getVoucherCodeId() {
        return voucherCodeId;
    }

    public void setVoucherCodeId(Long voucherCodeId) {
        this.voucherCodeId = voucherCodeId;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    
}
