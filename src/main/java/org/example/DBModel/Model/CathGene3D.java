package org.example.DBModel.Model;

import org.example.DBModel.Model.BasicItem;

import java.util.LinkedList;
import java.util.List;

public class CathGene3D extends BasicItem {
    private double eValue;
    private double score;

    protected class Location {
        private int start;
        private int end;
        private int hmmStart;
        private int hmmEnd;
        private int hmmLength;
        private String hmmBounds;
        private double evalue;
        private double score;  //specific
        private int envelopStart;
        private int envelopEnd;
        private boolean postProcessed;

        List<LocationFragment> fragments;

        public Location() {
            fragments = new LinkedList<LocationFragment>();
        }
    }
}
