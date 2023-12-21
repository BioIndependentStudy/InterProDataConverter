package org.example.DBModel.Model;

import org.example.DBModel.Model.BasicItem;

import java.util.LinkedList;
import java.util.List;

public class TMHelix extends BasicItem {

    protected class Location {
        private int start;
        private int end;
        private String prediction;
        private double score;   //specific property
        List<LocationFragment> fragments;

        public Location() {
            fragments = new LinkedList<LocationFragment>();
        }
    }

}
