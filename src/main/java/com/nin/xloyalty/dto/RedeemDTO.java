package com.nin.xloyalty.dto;

public class RedeemDTO {
    Integer loyaltyProgramId;
    Integer availableVoucher;


    public Integer getLoyaltyProgramId() {
        return loyaltyProgramId;
    }

    public void setLoyaltyProgramId(Integer loyaltyProgramId) {
        this.loyaltyProgramId = loyaltyProgramId;
    }

    public Integer getAvailableVoucher() {
        return availableVoucher;
    }

    public void setAvailableVoucher(Integer availableVoucher) {
        this.availableVoucher = availableVoucher;
    }
}

