package com.rit.tcs.bussinessobject;

/**
 * Created by A688629 on 05-10-2018.
 */

public class Employement {
    private String id;

    private String totalNumberofyear;

    private String joindate;

    private String company;

    private String designation;

    private String releasedate;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTotalNumberofyear ()
    {
        return totalNumberofyear;
    }

    public void setTotalNumberofyear (String totalNumberofyear)
    {
        this.totalNumberofyear = totalNumberofyear;
    }

    public String getJoindate ()
    {
        return joindate;
    }

    public void setJoindate (String joindate)
    {
        this.joindate = joindate;
    }

    public String getCompany ()
    {
        return company;
    }

    public void setCompany (String company)
    {
        this.company = company;
    }

    public String getDesignation ()
    {
        return designation;
    }

    public void setDesignation (String designation)
    {
        this.designation = designation;
    }

    public String getReleasedate ()
    {
        return releasedate;
    }

    public void setReleasedate (String releasedate)
    {
        this.releasedate = releasedate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", totalNumberofyear = "+totalNumberofyear+", joindate = "+joindate+", company = "+company+", designation = "+designation+", releasedate = "+releasedate+"]";
    }
}
