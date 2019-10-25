package com.rit.tcs.bussinessobject;

public class LoginModel {
    private String message;

    private String status;

    private String token;

    private Lockers[] lockers;

    private Profile profile;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Lockers[] getLockers() {
        return lockers;
    }

    public void setLockers(Lockers[] lockers) {
        this.lockers = lockers;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + ", status = " + status + ", lockers = " + lockers + ", profile = " + profile + "]";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

