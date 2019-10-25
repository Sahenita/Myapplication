package com.rit.tcs.bussinessobject;

/**
 * Created by A688629 on 03-10-2018.
 */

public class CompanyLockerModel {
    private String message;

    private String status;

    private Lockers[] lockers;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Lockers[] getLockers ()
    {
        return lockers;
    }

    public void setLockers (Lockers[] lockers)
    {
        this.lockers = lockers;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", status = "+status+", lockers = "+lockers+"]";
    }
}
