
package com.rit.tcs.bussinessobject.LoginFirstModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Companydevice {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("macid")
    @Expose
    private String macid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMacid() {
        return macid;
    }

    public void setMacid(String macid) {
        this.macid = macid;
    }

}
