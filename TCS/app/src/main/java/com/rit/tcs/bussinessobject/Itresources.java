package com.rit.tcs.bussinessobject;

/**
 * Created by A688629 on 02-10-2018.
 */

public class Itresources {

    private boolean isFadoClosed = true;
    private int id;
    private int booked_quantity;
    private int outofstock;
    private int image;
    private int selectedImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBooked_quantity() {
        return booked_quantity;
    }

    public void setBooked_quantity(int booked_quantity) {
        this.booked_quantity = booked_quantity;
    }

    public int getOutofstock() {
        return outofstock;
    }

    public void setOutofstock(int outofstock) {
        this.outofstock = outofstock;
    }

    public int getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }

    private int available_quantity;

    private String bookingId;

    private String rfid;

    private String billing_amount;

    private String totalhour;

    private String pin;

    private String booking_date;

    private String hourly_rate;

    private String type;

    private String release_time;

    private String uniqueId;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getBilling_amount() {
        return billing_amount;
    }

    public void setBilling_amount(String billing_amount) {
        this.billing_amount = billing_amount;
    }

    public String getTotalhour() {
        return totalhour;
    }

    public void setTotalhour(String totalhour) {
        this.totalhour = totalhour;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(String hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public String toString() {
        return "ClassPojo [bookingId = " + bookingId + ", rfid = " + rfid + ", billing_amount = " + billing_amount + ", totalhour = " + totalhour + ", pin = " + pin + ", booking_date = " + booking_date + ", hourly_rate = " + hourly_rate + ", type = " + type + ", release_time = " + release_time + ", uniqueId = " + uniqueId + "]";
    }

    public boolean isFadoClosed() {
        return isFadoClosed;
    }

    public void setFadoClosed(boolean fadoClosed) {
        isFadoClosed = fadoClosed;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public int getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(int selectedImage) {
        this.selectedImage = selectedImage;
    }

}
