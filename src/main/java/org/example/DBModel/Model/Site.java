package org.example.DBModel.Model;

import org.example.DBModel.SiteLocation;

import java.util.LinkedList;

public class Site {
    private String description;
    private int numLocations;
    private LinkedList<SiteLocation> siteLocations;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumLocations() {
        return numLocations;
    }

    public void setNumLocations(int numLocations) {
        this.numLocations = numLocations;
    }

    public LinkedList<SiteLocation> getSiteLocations() {
        return siteLocations;
    }

    public void setSiteLocations(LinkedList<SiteLocation> siteLocations) {
        this.siteLocations = siteLocations;
    }

    public Site() {
        siteLocations = new LinkedList<SiteLocation>();
    }
}
