package com.rit.tcs.bussinessobject;

/**
 * Created by SONU on 25/09/15.
 */
public class Myassets {

    private boolean selected = false;

    private String start_time;

    private String category_name;

    private String serialnumber;

    private String category_id;

    private String name;

    private String end_time;

    private String id;

    private String passcode;

    private String locker_number;
    private String value = " ";
    private Boolean occupency;
    private Integer booking_id = 0;
    String selected1="";

    public String getSelected1() {
        return selected1;
    }

    public void setSelected1(String selected1) {
        this.selected1 = selected1;
    }

    public Integer getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Integer booking_id) {
        this.booking_id = booking_id;
    }

    public Boolean getOccupency() {
        return occupency;
    }

    public void setOccupency(Boolean occupency) {
        this.occupency = occupency;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getLocker_number() {
        return locker_number;
    }

    public void setLocker_number(String locker_number) {
        this.locker_number = locker_number;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "ClassPojo [start_time = " + start_time + ", category_name = " + category_name + ", serialnumber = " + serialnumber + ", category_id = " + category_id + ", name = " + name + ", end_time = " + end_time + ", id = " + id + ", passcode = " + passcode + ", locker_number = " + locker_number + ", occupency = " + occupency + ", booking_id = " + booking_id + "]";
    }
}
