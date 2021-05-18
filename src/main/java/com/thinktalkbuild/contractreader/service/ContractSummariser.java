package com.thinktalkbuild.contractreader.service;

import com.thinktalkbuild.contractreader.model.ContractSummary;
import com.thinktalkbuild.contractreader.model.ContractSection;
import com.thinktalkbuild.contractreader.model.SearchConfig;
import com.thinktalkbuild.contractreader.model.SearchItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author kate
 */
public class ContractSummariser {

    protected List<String> findParagraphsContainingAnyOfTheseWords(List<String> inputParagraphs, List<String> words) {

        return inputParagraphs.stream().filter (
                    para -> containsOneOrMore(para, words)
                )
                .collect(Collectors.toList());

    }

    private boolean containsOneOrMore(String paragraph, List<String> words) {
        return words.stream().anyMatch(word -> paragraph.contains(word));// return early, found one.  Possible future dev: continue searching e.g. to highlight all?
       
    }
}