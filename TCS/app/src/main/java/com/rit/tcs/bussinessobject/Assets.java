package com.rit.tcs.bussinessobject;

import java.io.Serializable;

public class Assets implements Serializable {
    private String name;

    private String serial_number;

    private String id;

    private String locker_number;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getSerial_number ()
    {
        return serial_number;
    }

    public void setSerial_number (String serial_number)
    {
        this.serial_number = serial_number;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getLocker_number ()
    {
        return locker_number;
    }

    public void setLocker_number (String locker_number)
    {
        this.locker_number = locker_number;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", serial_number = "+serial_number+", id = "+id+", locker_number = "+locker_number+"]";
    }
}
