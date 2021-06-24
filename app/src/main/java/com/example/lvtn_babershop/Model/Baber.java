package com.example.lvtn_babershop.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Baber implements Parcelable {
    private  String nameBaber, username, password, BaberId;
    private Long rating;

    public Baber() {
    }


    protected Baber(Parcel in) {
        nameBaber = in.readString();
        username = in.readString();
        password = in.readString();
        BaberId = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readLong();
        }
    }

    public static final Creator<Baber> CREATOR = new Creator<Baber>() {
        @Override
        public Baber createFromParcel(Parcel in) {
            return new Baber(in);
        }

        @Override
        public Baber[] newArray(int size) {
            return new Baber[size];
        }
    };

    public String getNameBaber() {
        return nameBaber;
    }

    public void setNameBaber(String nameBaber) {
        this.nameBaber = nameBaber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public String getBaberId() {
        return BaberId;
    }

    public void setBaberId(String baberId) {
        BaberId = baberId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameBaber);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(BaberId);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(rating);
        }
    }
}
