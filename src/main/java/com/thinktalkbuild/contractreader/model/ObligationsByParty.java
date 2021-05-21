package com.thinktalkbuild.contractreader.model;

import java.util.*;

public class ObligationsByParty {

    private List<Obligation> rawObligations;
    private Map<String, List<Obligation>> sortedObligations = new HashMap<>();

    public ObligationsByParty(List<Obligation> rawObligations){
        this.rawObligations=rawObligations;
       sort(rawObligations);
    }
    private void sort(List<Obligation> rawObligations){
        for(Obligation obl: rawObligations){
            String party = obl.getParty().toUpperCase();
            if(sortedObligations.containsKey(party)){
                sortedObligations.get(party).add(obl);// add one to list
            }else{
                sortedObligations.put(party, new ArrayList<>(List.of(obl))); // start new list
            }
        }
    }

    public List<Obligation> getRawObligations() {
        return rawObligations;
    }

    public Map<String, List<Obligation>> getSortedObligations() {
        return sortedObligations;
    }
}
