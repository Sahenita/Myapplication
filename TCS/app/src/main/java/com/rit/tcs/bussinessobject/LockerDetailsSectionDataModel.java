package com.rit.tcs.bussinessobject;

import java.util.List;

/**
 * Created by A688629 on 07-08-2018.
 */
public class LockerDetailsSectionDataModel {

    private String headerTitle;
    private List<Itresources> itresources;
    private List<Space> spaces;
    private int type;

    public LockerDetailsSectionDataModel() {

    }

    public LockerDetailsSectionDataModel(int type, String headerTitle, List<Itresources> getContestEntries, List<Space> howWorks) {
        this.type = type;
        this.headerTitle = headerTitle;
        this.itresources = getContestEntries;
        this.spaces = howWorks;
    }


    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }


    public List<Itresources> getItresources() {
        return itresources;
    }

    public void setItresources(List<Itresources> itresources) {
        this.itresources = itresources;
    }

    public List<Space> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<Space> spaces) {
        this.spaces = spaces;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}