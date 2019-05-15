package org.advertisment.entities;

import java.math.BigDecimal;

public class BannerAPI extends Banner{
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BannerAPI(Integer id, String name, String content, BigDecimal price, String category) {
        super(id, name, content, price);
        this.category = category;
    }
}
