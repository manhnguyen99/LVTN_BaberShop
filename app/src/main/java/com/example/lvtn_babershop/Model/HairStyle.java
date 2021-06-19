package com.example.lvtn_babershop.Model;

public class HairStyle {
    private String hairName;
    private String hairImage;
    private String hairInfo;

    public HairStyle() {
    }

    public HairStyle(String hairName, String hairImage, String hairInfo) {
        this.hairName = hairName;
        this.hairImage = hairImage;
        this.hairInfo = hairInfo;
    }
    public String getHairName() {
        return hairName;
    }

    public void setHairName(String hairName) {
        this.hairName = hairName;
    }

    public String getHairImage() {
        return hairImage;
    }

    public void setHairImage(String hairImage) {
        this.hairImage = hairImage;
    }

    public String getHairInfo() {
        return hairInfo;
    }

    public void setHairInfo(String hairInfo) {
        this.hairInfo = hairInfo;
    }
}
