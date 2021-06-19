package com.example.lvtn_babershop.Model;

public class Salon {
    private String nameSalon, addressSalon;

    public Salon() {
    }

    public Salon(String nameSalon, String addressSalon) {
        this.nameSalon = nameSalon;
        this.addressSalon = addressSalon;
    }

    public String getNameSalon() {
        return nameSalon;
    }

    public void setNameSalon(String nameSalon) {
        this.nameSalon = nameSalon;
    }

    public String getAddressSalon() {
        return addressSalon;
    }

    public void setAddressSalon(String addressSalon) {
        this.addressSalon = addressSalon;
    }
}
