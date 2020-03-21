package com.nin.xloyalty.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "app_data_config")
@Where(clause = "is_deleted='false'")
public class AppDataConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appDataConfigId;
    private String bannerHeaderImg;
    private String interestedFields;
    private String logo;
    private String privacyLink;
    private String termConditionsLink;
    private Boolean isActive;
    private Boolean isDeleted;

    public AppDataConfig(){

    }

    public AppDataConfig(Long appDataConfigId, String bannerHeaderImg, String interestedFields, String logo, String privacyLink, String termConditionsLink, Boolean isActive, Boolean isDeleted) {
        this.appDataConfigId = appDataConfigId;
        this.bannerHeaderImg = bannerHeaderImg;
        this.interestedFields = interestedFields;
        this.logo = logo;
        this.privacyLink = privacyLink;
        this.termConditionsLink = termConditionsLink;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }

    public Long getAppDataConfigId() {
        return appDataConfigId;
    }

    public void setAppDataConfigId(Long appDataConfigId) {
        this.appDataConfigId = appDataConfigId;
    }

    public String getBannerHeaderImg() {
        return bannerHeaderImg;
    }

    public void setBannerHeaderImg(String bannerHeaderImg) {
        this.bannerHeaderImg = bannerHeaderImg;
    }

    public String getInterestedFields() {
        return interestedFields;
    }

    public void setInterestedFields(String interestedFields) {
        this.interestedFields = interestedFields;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPrivacyLink() {
        return privacyLink;
    }

    public void setPrivacyLink(String privacyLink) {
        this.privacyLink = privacyLink;
    }

    public String getTermConditionsLink() {
        return termConditionsLink;
    }

    public void setTermConditionsLink(String termConditionsLink) {
        this.termConditionsLink = termConditionsLink;
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
