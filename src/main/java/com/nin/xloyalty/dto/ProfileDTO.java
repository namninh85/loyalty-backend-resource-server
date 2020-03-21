package com.nin.xloyalty.dto;

import java.util.ArrayList;
import java.util.List;

public class ProfileDTO {
	String phone;
	String address;
	String language;
	String avatarImage;
	String bannerHeaderImage;
	List<Interert> interestedFields;
	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAvatarImage() {
		return avatarImage;
	}

	public void setAvatarImage(String avatarImage) {
		this.avatarImage = avatarImage;
	}

	public String getBannerHeaderImage() {
		return bannerHeaderImage;
	}

	public void setBannerHeaderImage(String bannerHeaderImage) {
		this.bannerHeaderImage = bannerHeaderImage;
	}

	public List<Interert> getInterestedFields() {
		return interestedFields;
	}

	public void setInterestedFields(List<Interert> interestedFields) {
		this.interestedFields = interestedFields;
	}

	public String getStringInterestedFields() {
		if(this.interestedFields != null && this.interestedFields.size() > 0) {
			List<String> interests = new ArrayList<String>();
			for (Interert interert : this.interestedFields) {
				interests.add(interert.getValue());
			}
			if (interests.size() > 0) {
				return String.join(",", interests);
			}
		}
		
		return "";
	}

	class Interert {
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
