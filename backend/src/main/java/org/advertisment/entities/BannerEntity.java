package org.advertisment.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "banner")
public class BannerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;

    @Column(name = "content")
    private String content;

    @Column(name = "deleted")
    private Boolean deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public BannerEntity() {
    }

    public BannerEntity(String name, BigDecimal price, CategoryEntity category, String content, Boolean deleted) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.content = content;
        this.deleted = deleted;
    }

    public BannerEntity(Integer id, String name, BigDecimal price, CategoryEntity category,   String content) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.content = content;
        this.deleted = false;
    }

    public BannerEntity(Integer id, String name, BigDecimal price, CategoryEntity category, String content, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.content = content;
        this.deleted = deleted;
    }
}
