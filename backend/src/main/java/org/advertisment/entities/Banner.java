package org.advertisment.entities;

import java.math.BigDecimal;

public class Banner {
    private Integer id;
    private String name;
    private String content;
    private BigDecimal price;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Banner() {
    }

    public Banner(Integer id, String name, String content, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.price = price;
    }
}
