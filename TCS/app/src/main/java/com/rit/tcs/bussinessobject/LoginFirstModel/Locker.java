
package com.rit.tcs.bussinessobject.LoginFirstModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Locker {

    @SerializedName("fadotype")
    @Expose
    private Integer fadotype;
    @SerializedName("itresources")
    @Expose
    private List<Object> itresources = null;
    @SerializedName("space")
    @Expose
    private List<Object> space = null;

    public Integer getFadotype() {
        return fadotype;
    }

    public void setFadotype(Integer fadotype) {
        this.fadotype = fadotype;
    }

    public List<Object> getItresources() {
        return itresources;
    }

    public void setItresources(List<Object> itresources) {
        this.itresources = itresources;
    }

    public List<Object> getSpace() {
        return space;
    }

    public void setSpace(List<Object> space) {
        this.space = space;
    }

}
