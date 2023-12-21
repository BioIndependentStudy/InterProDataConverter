package org.example.DBModel.Model;

import org.example.DBModel.Model.BasicItem;

import java.util.LinkedList;
import java.util.List;

public class SignalPNoTM extends BasicItem {
    private String orgType = "";


    protected class Location {
        private int start;
        private int end;
        private double score;   //specific property
        List<BasicItem.LocationFragment> fragments;

        public Location() {
            fragments = new LinkedList<BasicItem.LocationFragment>();
        }
    }

}
