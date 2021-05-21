package com.thinktalkbuild.contractreader.model;

public class Obligation {

    private String wholeSentence;
    private String party;
    private String obligingVerb;
    private String action;

    private String wholeSentenceHighlighted;

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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getWholeSentenceHighlighted() {
        return wholeSentenceHighlighted;
    }

    public void setWholeSentenceHighlighted(String wholeSentenceHighlighted) {
        this.wholeSentenceHighlighted = wholeSentenceHighlighted;
    }
}
