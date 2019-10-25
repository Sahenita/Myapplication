package com.rit.tcs.bussinessobject;

/**
 * Created by A688629 on 05-10-2018.
 */

public class ProfileFull {
    private Employement[] employement;
    private String current_company;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public String getCurrent_company() {
        return current_company;
    }

    public void setCurrent_company(String current_company) {
        this.current_company = current_company;
    }

    public String getCurrent_designation() {
        return current_designation;
    }

    public void setCurrent_designation(String current_designation) {
        this.current_designation = current_designation;
    }

    private String current_designation;
    private String phone;

    private History[] History;

    private String email;

    private String about;

    private Current_usage[] current_usage;

    private String fullname;

    public Employement[] getEmployement() {
        return employement;
    }

    public void setEmployement(Employement[] employement) {
        this.employement = employement;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public History[] getHistory() {
        return History;
    }

    public void setHistory(History[] History) {
        this.History = History;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Current_usage[] getCurrent_usage() {
        return current_usage;
    }

    public void setCurrent_usage(Current_usage[] current_usage) {
        this.current_usage = current_usage;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return "ClassPojo [employement = " + employement + ", phone = " + phone + ", History = " + History + ", email = " + email + ", about = " + about + ", current_usage = " + current_usage + ", fullname = " + fullname + "]";
    }
}
