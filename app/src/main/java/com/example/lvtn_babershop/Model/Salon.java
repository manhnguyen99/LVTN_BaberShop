package com.example.lvtn_babershop.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Salon implements Parcelable {

    private String nameSalon, addressSalon, salonID;

    public Salon() {
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

    public String getSalonID() {
        return salonID;
    }

    public void setSalonID(String salonID) {
        this.salonID = salonID;
    }


    protected Salon(Parcel in) {
        nameSalon = in.readString();
        addressSalon = in.readString();
        salonID = in.readString();
    }

    public static final Creator<Salon> CREATOR = new Creator<Salon>() {
        @Override
        public Salon createFromParcel(Parcel in) {
            return new Salon(in);
        }

        @Override
        public Salon[] newArray(int size) {
            return new Salon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameSalon);
        dest.writeString(addressSalon);
        dest.writeString(salonID);
    }
}
