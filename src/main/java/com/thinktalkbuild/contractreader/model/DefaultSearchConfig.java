package com.thinktalkbuild.contractreader.model;

import com.thinktalkbuild.contractreader.model.SearchCriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DefaultSearchConfig implements SearchConfig{

    private List<SearchCriteria> searchCriteria = new ArrayList<>();

    public DefaultSearchConfig() { // TODO: read from yaml
        searchCriteria.add(new SearchCriteria("Termination", Collections.singletonList("termination")));
        searchCriteria.add(new SearchCriteria("Liability", Arrays.asList(new String[]{"liability", "liable"})));
        searchCriteria.add(new SearchCriteria("Warranties", Arrays.asList(new String[]{"warrants", "warranty", "warranties"})));
        searchCriteria.add(new SearchCriteria("Indemnities", Arrays.asList(new String[]{"indemnifies", "indemnity", "indemnities"})));
        searchCriteria.add(new SearchCriteria("Price", Arrays.asList(new String[]{"charges", "fees", "price"})));
        searchCriteria.add(new SearchCriteria("Licences", Arrays.asList(new String[]{"licence", "license", "assign"})));
        searchCriteria.add(new SearchCriteria("Consents", Arrays.asList(new String[]{"consent", "approve", "approval"})));
    }

    public List<SearchCriteria> getSearchCriteria() {
        return searchCriteria;
    }
}
