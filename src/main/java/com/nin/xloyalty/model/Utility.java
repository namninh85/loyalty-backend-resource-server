package com.nin.xloyalty.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "utility")
@Where(clause = "is_deleted='false'")
public class Utility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long utilityId;
    private String name;
    private String image;
    private String webLink;
    private Boolean isActive;
    private Boolean isDeleted;

    public Utility(Long utilityId, String name, String image, String webLink, Boolean isActive, Boolean isDeleted) {
        this.utilityId = utilityId;
        this.name = name;
        this.image = image;
        this.webLink = webLink;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }

    public Long getUtilityId() {
        return utilityId;
    }

    public void setUtilityId(Long utilityId) {
        this.utilityId = utilityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
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
