package com.nin.xloyalty.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "customer_rewards_log")
public class CustomerRewardsLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerRewardsLogId;
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @Column(name = "loyalty_program_id", nullable = false)
    private Long loyaltyProgramId;
    private Long voucherCodeId;
    private Integer pointBurnEarn;
    private Date rewardDate;
    private Boolean isActive;
    private Boolean isDeleted;

    public CustomerRewardsLog() {
    }

    public CustomerRewardsLog(Long customerRewardsLogId, Long customerId, Long loyaltyProgramId, Long voucherCodeId, Integer pointBurnEarn, Date rewardDate, Boolean isActive, Boolean isDeleted) {
        this.customerRewardsLogId = customerRewardsLogId;
        this.customerId = customerId;
        this.loyaltyProgramId = loyaltyProgramId;
        this.voucherCodeId = voucherCodeId;
        this.pointBurnEarn = pointBurnEarn;
        this.rewardDate = rewardDate;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }

    public Long getCustomerRewardsLogId() {
        return customerRewardsLogId;
    }

    public void setCustomerRewardsLogId(Long customerRewardsLogId) {
        this.customerRewardsLogId = customerRewardsLogId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getLoyaltyProgramId() {
        return loyaltyProgramId;
    }

    public void setLoyaltyProgramId(Long loyaltyProgramId) {
        this.loyaltyProgramId = loyaltyProgramId;
    }

    public Long getVoucherCodeId() {
        return voucherCodeId;
    }

    public void setVoucherCodeId(Long voucherCodeId) {
        this.voucherCodeId = voucherCodeId;
    }

    public Integer getPointBurnEarn() {
        return pointBurnEarn;
    }

    public void setPointBurnEarn(Integer pointBurnEarn) {
        this.pointBurnEarn = pointBurnEarn;
    }

    public Date getRewardDate() {
        return rewardDate;
    }

    public void setRewardDate(Date rewardDate) {
        this.rewardDate = rewardDate;
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
