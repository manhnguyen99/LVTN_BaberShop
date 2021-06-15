package com.example.lvtn_babershop.Model;

public class product {
    String image;
    String nameProduct;
    String infoProduct;
    String price;

    public product() {
    }

    public product(String image, String infoProduct, String price, String nameProduct) {
        this.image = image;
        this.infoProduct = infoProduct;
        this.price = price;
        this.nameProduct = nameProduct;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInfoProduct() {
        return infoProduct;
    }

    public void setInfoProduct(String infoProduct) {
        this.infoProduct = infoProduct;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    @Override
    public String toString() {
        return "$  " + price;
    }
}
