package com.nin.xloyalty.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "customer_has_voucher")
public class CustomerHasVoucher implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerHasVoucherId;
    private Long customerId;
    private Long voucherCodeId;
    private Date receivedDate;
    private Date expiredDate;
    private Boolean isActive;
    private Boolean isDeleted;

    public CustomerHasVoucher(){}

    public CustomerHasVoucher(Long customerHasVoucherId, Long customerId, Long voucherCodeId, Date receivedDate, Date expiredDate, Boolean isActive, Boolean isDeleted) {
        this.customerHasVoucherId = customerHasVoucherId;
        this.customerId = customerId;
        this.voucherCodeId = voucherCodeId;
        this.receivedDate = receivedDate;
        this.expiredDate = expiredDate;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }

    public Long getCustomerHasVoucherId() {
        return customerHasVoucherId;
    }

    public void setCustomerHasVoucherId(Long customerHasVoucherId) {
        this.customerHasVoucherId = customerHasVoucherId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getVoucherCodeId() {
        return voucherCodeId;
    }

    public void setVoucherCodeId(Long voucherCodeId) {
        this.voucherCodeId = voucherCodeId;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
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
