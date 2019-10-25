package com.rit.tcs.bussinessobject;

public class RFIDDataModel {
    private String secretkey;

    private Profile[] profile;

    private Allassets[] allassets;

    private String message;

    private Myassets[] myassets;

    private String status;

    public String getSecretkey ()
    {
        return secretkey;
    }

    public void setSecretkey (String secretkey)
    {
        this.secretkey = secretkey;
    }

    public Profile[] getProfile ()
    {
        return profile;
    }

    public void setProfile (Profile[] profile)
    {
        this.profile = profile;
    }

    public Allassets[] getAllassets ()
    {
        return allassets;
    }

    public void setAllassets (Allassets[] allassets)
    {
        this.allassets = allassets;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Myassets[] getMyassets ()
    {
        return myassets;
    }

    public void setMyassets (Myassets[] myassets)
    {
        this.myassets = myassets;
    }

    public String getStatus ()
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
        return "ClassPojo [secretkey = "+secretkey+", profile = "+profile+", allassets = "+allassets+", message = "+message+", myassets = "+myassets+", status = "+status+"]";
    }
}
