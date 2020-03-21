package com.nin.xloyalty.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "voucher")
@Where(clause = "is_deleted='false'")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voucherId;
    private String name;
    private String description;
    private String image;
    private Integer value;
    private String currency;
    private Integer numberDateUse;
    private Boolean isActive;
    private Boolean isDeleted;

    
    public Voucher(){

    }

    public Voucher(Long voucherId, String name, String description, String image, Integer value, String currency, Integer numberDateUse, Boolean isActive, Boolean isDeleted) {
        this.voucherId = voucherId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.value = value;
        this.currency = currency;
        this.numberDateUse = numberDateUse;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
       
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getNumberDateUse() {
        return numberDateUse;
    }

    public void setNumberDateUse(Integer numberDateUse) {
        this.numberDateUse = numberDateUse;
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
