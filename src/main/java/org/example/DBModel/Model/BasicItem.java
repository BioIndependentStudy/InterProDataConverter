package org.example.DBModel.Model;

import java.util.LinkedList;
import java.util.List;

public class BasicItem {
    private String ref = "";
    private String sequence = "";
    private String accession = "";
    private String name = "";
    private String description = "";
    private String library = "";
    private String version = "";
    private String entry = "";
    private String modelAC = "";
    private List<Location> locations;

    public BasicItem() {
        locations = new LinkedList<Location>();
    }

    protected class Location {
        private int start;
        private int end;
        private String sequenceFeature;
        List<LocationFragment> fragments;

        public Location() {
            fragments = new LinkedList<LocationFragment>();
        }
    }

    protected class LocationFragment {
        private int start;
        private int end;
        private String dcStatus;
    }

}
