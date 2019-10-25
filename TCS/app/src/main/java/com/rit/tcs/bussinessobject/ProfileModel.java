package com.rit.tcs.bussinessobject;

import com.google.gson.annotations.SerializedName;

/**
 * Created by A688629 on 05-10-2018.
 */

public class ProfileModel {
    private String message;

    private String status;

    @SerializedName("profile")
    private ProfileFull[] profile;

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

    public ProfileFull[] getProfile ()
    {
        return profile;
    }

    public void setProfile (ProfileFull[] profile)
    {
        this.profile = profile;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", status = "+status+", profile = "+profile+"]";
    }
}
