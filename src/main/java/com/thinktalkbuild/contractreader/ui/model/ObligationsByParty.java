package com.thinktalkbuild.contractreader.ui.model;

import java.util.*;

public class ObligationsByParty {

    private Map<String, List<Obligation>> sortedObligations = new HashMap<>();

    public ObligationsByParty(Map<String, List<Obligation>> sortedObligations){
       this.sortedObligations=sortedObligations;
    }

    public ObligationsByParty(){};
    public Map<String, List<Obligation>> getSortedObligations() {
        return sortedObligations;
    }
}
