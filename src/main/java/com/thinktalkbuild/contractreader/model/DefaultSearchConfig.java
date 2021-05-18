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
    }

    public List<SearchCriteria> getSearchCriteria() {
        return searchCriteria;
    }
}
