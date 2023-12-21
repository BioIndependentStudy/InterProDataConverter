package org.example.DBModel;


import java.util.LinkedList;

public class Entry {
    private String accession;
    private String name;
    private String description;
    private String type;
    private LinkedList<GoXRefs> goXRefses;
    private LinkedList<PathwayXRefs> pathwayXRefses;

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LinkedList<GoXRefs> getGoXRefses() {
        return goXRefses;
    }

    public void setGoXRefses(LinkedList<GoXRefs> goXRefses) {
        this.goXRefses = goXRefses;
    }

    public LinkedList<PathwayXRefs> getPathwayXRefses() {
        return pathwayXRefses;
    }

    public void setPathwayXRefses(LinkedList<PathwayXRefs> pathwayXRefses) {
        this.pathwayXRefses = pathwayXRefses;
    }

    public Entry() {
        this.goXRefses = new LinkedList<GoXRefs>();
        this.pathwayXRefses = new LinkedList<PathwayXRefs>();
    }
}
