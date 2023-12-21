package org.example.DBModel;

import java.util.LinkedList;
import java.util.List;

public class ProteinHit {
    private String applications;
    private String interproscanVersion;
    private LinkedList<Result> proteinSearchResults;

    public String getApplications() {
        return applications;
    }

    public void setApplications(String applications) {
        this.applications = applications;
    }

    public String getInterproscanVersion() {
        return interproscanVersion;
    }

    public void setInterproscanVersion(String interproscanVersion) {
        this.interproscanVersion = interproscanVersion;
    }

    public LinkedList<Result> getResults() {
        return proteinSearchResults;
    }

    public void setResults(LinkedList<Result> proteinSearchResults) {
        this.proteinSearchResults = proteinSearchResults;
    }

    public ProteinHit() {
        proteinSearchResults = new LinkedList<Result>();
    }
}
