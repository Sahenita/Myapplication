package com.rit.tcs.bussinessobject;

/**
 * Created by SONU on 25/09/15.
 */
public class Companydevices {

    private String macid;

    private String id;

    public String getMacid ()
    {
        return macid;
    }

    public void setMacid (String macid)
    {
        this.macid = macid;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [macid = "+macid+", id = "+id+"]";
    }
}
