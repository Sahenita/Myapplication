package com.rit.tcs.bussinessobject;

import java.io.Serializable;

public class Allassets implements Serializable {
    private String catid;

    public String getCat_image() {
        return cat_image;
    }

    public void setCat_image(String cat_image) {
        this.cat_image = cat_image;
    }

    private String cat_image;

    private Assets[] assets;

    private String cat_name;
    private String value=" ";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String getCatid ()
    {
        return catid;
    }

    public void setCatid (String catid)
    {
        this.catid = catid;
    }

    public Assets[] getAssets ()
    {
        return assets;
    }

    public void setAssets (Assets[] assets)
    {
        this.assets = assets;
    }

    public String getCat_name ()
    {
        return cat_name;
    }

    public void setCat_name (String cat_name)
    {
        this.cat_name = cat_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [catid = "+catid+", assets = "+assets+", cat_name = "+cat_name+"]";
    }
}
