package com.rit.tcs.bussinessobject;

/**
 * Created by SONU on 25/09/15.
 */
public class TimeDataModel {

    // Getter and Setter model for recycler view items
    private float rate;
    private long hr;
    private long min;
    private String price;
    private String startDate;
    private String endDate;
    private boolean selected;

    public TimeDataModel(float rate, long hr, long min, String price) {
        this.rate = rate;
        this.hr = hr;
        this.min = min;
        this.price = price;
        this.selected = false;
    }

    public float getRate() {
        return rate;
    }


    public long getHr() {
        return hr;
    }

    public void setHr(long hr) {
        this.hr = hr;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
