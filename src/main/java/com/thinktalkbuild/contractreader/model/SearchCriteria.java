package com.thinktalkbuild.contractreader.model;

import java.util.List;

public class SearchCriteria {

    String title;
    List<String> wordsToSearchFor;

    public SearchCriteria(String title, List<String> wordsToSearchFor) {
        this.title = title;
        this.wordsToSearchFor = wordsToSearchFor;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getWordsToSearchFor() {
        return wordsToSearchFor;
    }


}
