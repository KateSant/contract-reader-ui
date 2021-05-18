package com.thinktalkbuild.contractreader.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchConfig {

    private List<SearchItem> searchItems = new ArrayList<>();

    public SearchConfig() { // TODO: read from yaml
        searchItems.add(new SearchItem("Termination", Collections.singletonList("termination")));
        searchItems.add(new SearchItem("Liability", Arrays.asList(new String[]{"liability", "liable"})));
    }

    public List<SearchItem> getSearchSections() {
        return searchItems;
    }
}
