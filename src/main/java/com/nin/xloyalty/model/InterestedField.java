package com.nin.xloyalty.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "interested_field")
@Where(clause = "is_deleted='false'")
public class InterestedField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interestedFieldId;
    private String name;
    private Boolean isActive;
    private Boolean isDeleted;

    public InterestedField() {

    }

    public InterestedField(Long interestedFieldId, String name, Boolean isActive, Boolean isDeleted) {
        this.interestedFieldId = interestedFieldId;
        this.name = name;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }

    public Long getInterestedFieldId() {
        return interestedFieldId;
    }

    public void setInterestedFieldId(Long interestedFieldId) {
        this.interestedFieldId = interestedFieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
