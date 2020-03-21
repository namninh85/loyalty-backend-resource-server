package com.nin.xloyalty.dto;


import java.util.ArrayList;
import java.util.List;

public class AppDataConfigDTO {
    private Long appDataConfigId;
    private String bannerHeaderImg;
    private String logo;
    private String privacyLink;
    private String termConditionsLink;
    List<Interest> interestedFields;

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

    public void setInterestedFields(List<Interest> interestedFields) {
        this.interestedFields = interestedFields;
    }

    public String getStringInterestedFields() {
        if(this.interestedFields != null && this.interestedFields.size() > 0) {
            List<String> interests = new ArrayList<>();
            for (Interest interest : this.interestedFields) {
                interests.add(interest.getValue());
            }
            if (interests.size() > 0) {
                return String.join(",", interests);
            }
        }

        return "";
    }

    class Interest{
        String name;
        String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
