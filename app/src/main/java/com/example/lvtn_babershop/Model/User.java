package com.example.lvtn_babershop.Model;

public class User {
    private String Fname,Lname,City, EmailID, Mobile;

    public User() {
    }

    public User(String fname, String lname, String city, String emailID, String mobile) {
        Fname = fname;
        Lname = lname;
        City = city;
        EmailID = emailID;
        Mobile = mobile;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
