package com.nin.xloyalty.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_category")
@Where(clause = "is_deleted='false'")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCategoryId;
    private String code;
    private String name;
    private String description;
    private String image;
    private String webLink;
    private Long parentId;
    private Boolean isActive;
    private Boolean isDeleted;



    public Category() {
    }

    public Category(Long productCategoryId, String code, String name, String description, String image, String webLink,
                    Long parentId, Boolean isActive, Boolean isDeleted, List<Product> products) {
        this.productCategoryId = productCategoryId;
        this.code = code;
        this.name = name;
        this.description = description;
        this.image = image;
        this.webLink = webLink;
        this.parentId = parentId;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
       
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
