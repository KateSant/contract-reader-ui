package com.thinktalkbuild.contractreader.model;

import java.util.List;

public class SearchItem {

    String title;
    List<String> wordsToSearchFor;

    public SearchItem(String title, List<String> wordsToSearchFor) {
        this.title = title;
        this.wordsToSearchFor = wordsToSearchFor;
    }
}
