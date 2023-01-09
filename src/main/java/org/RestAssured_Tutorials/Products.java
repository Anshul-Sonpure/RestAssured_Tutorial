package org.RestAssured_Tutorials;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Products {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String title;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private double price;
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;
    private byte image;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getImage() {
        return image;
    }

    public void setImage(byte image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
