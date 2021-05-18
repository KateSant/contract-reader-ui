package com.thinktalkbuild.contractreader.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchConfig {

    private List<SearchCriteria> searchCriteria = new ArrayList<>();

    public SearchConfig() { // TODO: read from yaml
        searchCriteria.add(new SearchCriteria("Termination", Collections.singletonList("termination")));
        searchCriteria.add(new SearchCriteria("Liability", Arrays.asList(new String[]{"liability", "liable"})));
    }

    public List<SearchCriteria> getSearchSections() {
        return searchCriteria;
    }
}
