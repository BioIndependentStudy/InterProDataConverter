package org.example.DBModel;

import org.example.DBModel.Model.Site;

import java.util.LinkedList;

public class Location {
    private int start;
    private int end;
    private double score;
    private LinkedList<LocationFragment> locationFragments;
    private String sequenceFeature;
    private int hmmStart;
    private int hmmEnd;
    private int hmmLength;
    private String hmmBounds;
    private double eValue;
    private int envelopeStart;
    private int envelopeEnd;
    private  boolean isPostProcessed;
    private LinkedList<Site> sites;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public LinkedList<LocationFragment> getLocationFragments() {
        return locationFragments;
    }

    public void setLocationFragments(LinkedList<LocationFragment> locationFragments) {
        this.locationFragments = locationFragments;
    }

    public String getSequenceFeature() {
        return sequenceFeature;
    }

    public void setSequenceFeature(String sequenceFeature) {
        this.sequenceFeature = sequenceFeature;
    }

    public int getHmmStart() {
        return hmmStart;
    }

    public void setHmmStart(int hmmStart) {
        this.hmmStart = hmmStart;
    }

    public int getHmmEnd() {
        return hmmEnd;
    }

    public void setHmmEnd(int hmmEnd) {
        this.hmmEnd = hmmEnd;
    }

    public int getHmmLength() {
        return hmmLength;
    }

    public void setHmmLength(int hmmLength) {
        this.hmmLength = hmmLength;
    }

    public String getHmmBounds() {
        return hmmBounds;
    }

    public void setHmmBounds(String hmmBounds) {
        this.hmmBounds = hmmBounds;
    }

    public double geteValue() {
        return eValue;
    }

    public void seteValue(double eValue) {
        this.eValue = eValue;
    }

    public int getEnvelopeStart() {
        return envelopeStart;
    }

    public void setEnvelopeStart(int envelopeStart) {
        this.envelopeStart = envelopeStart;
    }

    public int getEnvelopeEnd() {
        return envelopeEnd;
    }

    public void setEnvelopeEnd(int envelopeEnd) {
        this.envelopeEnd = envelopeEnd;
    }

    public boolean isPostProcessed() {
        return isPostProcessed;
    }

    public void setPostProcessed(boolean postProcessed) {
        isPostProcessed = postProcessed;
    }

    public LinkedList<Site> getSites() {
        return sites;
    }

    public void setSites(LinkedList<Site> sites) {
        this.sites = sites;
    }

    public Location() {
        this.locationFragments = new LinkedList<>();
        this.sites = new LinkedList<>();
    }
}
