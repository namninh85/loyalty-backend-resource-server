package com.nin.xloyalty.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Where(clause = "is_deleted ='false'")
public class LoyaltyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loyaltyProgramId ;
    private Long voucherId;
    private Date startDate;
    private Date endDate;
    private Integer point;
    private Integer total_release;
    private Integer available;
    private Boolean isActive;
    private Boolean isDeleted;

    public LoyaltyProgram(){}

    public LoyaltyProgram(Long loyaltyProgramId, Long voucherId, Date startDate, Date endDate, Integer point, Integer total_release, Integer available, Boolean isActive, Boolean isDeleted) {
        this.loyaltyProgramId = loyaltyProgramId;
        this.voucherId = voucherId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.point = point;
        this.total_release = total_release;
        this.available = available;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        
    }

    public Long getLoyaltyProgramId() {
        return loyaltyProgramId;
    }

    public void setLoyaltyProgramId(Long loyaltyProgramId) {
        this.loyaltyProgramId = loyaltyProgramId;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getTotal_release() {
        return total_release;
    }

    public void setTotal_release(Integer total_release) {
        this.total_release = total_release;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
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