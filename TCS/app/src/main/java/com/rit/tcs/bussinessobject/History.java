package com.rit.tcs.bussinessobject;

/**
 * Created by A688629 on 05-10-2018.
 */

public class History { private String hourspent;

    private String TotalSpent;

    private String fadotype;

    private Space[] space;

    private Itresources[] itresources;

    public String getHourspent ()
    {
        return hourspent;
    }

    public void setHourspent (String hourspent)
    {
        this.hourspent = hourspent;
    }

    public String getTotalSpent ()
    {
        return TotalSpent;
    }

    public void setTotalSpent (String TotalSpent)
    {
        this.TotalSpent = TotalSpent;
    }

    public String getFadotype ()
    {
        return fadotype;
    }

    public void setFadotype (String fadotype)
    {
        this.fadotype = fadotype;
    }

    public Space[] getSpace ()
    {
        return space;
    }

    public void setSpace (Space[] space)
    {
        this.space = space;
    }

    public Itresources[] getItresources ()
    {
        return itresources;
    }

    public void setItresources (Itresources[] itresources)
    {
        this.itresources = itresources;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [hourspent = "+hourspent+", TotalSpent = "+TotalSpent+", fadotype = "+fadotype+", space = "+space+", itresources = "+itresources+"]";
    }
}