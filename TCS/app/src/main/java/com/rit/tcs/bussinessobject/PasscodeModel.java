package com.rit.tcs.bussinessobject;

/**
 * Created by SONU on 25/09/15.
 */
public class PasscodeModel {

    private String message;

    private String status;

    private String locker;

    private String macid;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus (                                                                                                                               )
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", status = "+status+"]";
    }

    public String getLocker() {
        return locker;
    }

    public void setLocker(String locker) {
        this.locker = locker;
    }

    public String getMacId() {
        return macid;
    }

    public void setMacId(String macid) {
        this.macid = macid;
    }
}
