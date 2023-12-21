package org.example.DBModel;

import java.util.LinkedList;
import java.util.List;

public class Result {
    private String sequence;
    private String md5;
    private LinkedList<Match> matches;
    private LinkedList<Xref> xrefs;

    public LinkedList<Xref> getXrefs() {
        return xrefs;
    }

    public void setXrefs(LinkedList<Xref> xrefs) {
        this.xrefs = xrefs;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public LinkedList<Match> getMatches() {
        return matches;
    }

    public void setMatches(LinkedList<Match> matches) {
        this.matches = matches;
    }

    public Result() {
        matches = new LinkedList<Match>();
        xrefs = new LinkedList<Xref>();
    }
}

