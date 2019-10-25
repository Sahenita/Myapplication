package com.rit.tcs.bussinessobject;

/**
 * Created by A688629 on 05-10-2018.
 */

public class Current_usage {
    private String fadotype;

    private Space[] space;

    private Itresources[] itresources;

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
        return "ClassPojo [fadotype = "+fadotype+", space = "+space+", itresources = "+itresources+"]";
    }
}
