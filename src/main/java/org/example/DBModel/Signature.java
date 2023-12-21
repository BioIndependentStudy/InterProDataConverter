package org.example.DBModel;

import java.util.LinkedList;

public class Signature {
    private String accession;
    private String name;
    private String description;
    private SignatureLibraryRelease signatureLibraryRelease;
    private Entry entry;

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

    public SignatureLibraryRelease getSignatureLibraryRelease() {
        return signatureLibraryRelease;
    }

    public void setSignatureLibraryRelease(SignatureLibraryRelease signatureLibraryRelease) {
        this.signatureLibraryRelease = signatureLibraryRelease;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public Signature() {
        signatureLibraryRelease = new SignatureLibraryRelease();
        entry = new Entry();
    }
}
