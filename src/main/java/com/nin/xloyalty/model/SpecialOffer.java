package com.nin.xloyalty.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "special_offer")
@Where(clause = "is_deleted = 'false'")
public class SpecialOffer implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 264068464920172308L;

	@Id
	private long specialOfferId;
	
	private String name;
	private String code;
	private String image;
	private String webLink;
	private Boolean isActive;
	
	public SpecialOffer() {
	}

	public SpecialOffer(int specialOfferId) {
		this.specialOfferId = specialOfferId;
	}

	public long getSpecialOfferId() {
		return specialOfferId;
	}

	public void setSpecialOfferId(long specialOfferId) {
		this.specialOfferId = specialOfferId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Boolean getIsActive() {
		if(this.isActive == null) return false;
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}	
	

}
