package org.example.DBModel;

import org.example.DBModel.Model.BasicItem;

import java.util.LinkedList;
import java.util.List;

public class Match {
    private Signature signature;
    private LinkedList<Location> locations;
    private String modelAc;
    private String orgType;

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public LinkedList<Location> getLocations() {
        return locations;
    }

    public void setLocations(LinkedList<Location> locations) {
        this.locations = locations;
    }

    public String getModelAc() {
        return modelAc;
    }

    public void setModelAc(String modelAc) {
        this.modelAc = modelAc;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Match() {
        this. signature = new Signature();
        locations = new LinkedList<Location>();
    }


}
