package com.thinktalkbuild.contractreader.model;

public class Obligation {

    private String wholeSentence;
    private String obligingVerb;
    private String party;

    public String getWholeSentence() {
        return wholeSentence;
    }

    public void setWholeSentence(String wholeSentence) {
        this.wholeSentence = wholeSentence;
    }

    public String getObligingVerb() {
        return obligingVerb;
    }

    public void setObligingVerb(String obligingVerb) {
        this.obligingVerb = obligingVerb;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }
}
