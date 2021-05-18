package com.thinktalkbuild.contractreader.service;

import com.thinktalkbuild.contractreader.model.ContractSummary;
import com.thinktalkbuild.contractreader.model.ContractSection;
import com.thinktalkbuild.contractreader.model.SearchConfig;
import com.thinktalkbuild.contractreader.model.SearchItem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kate
 */
public class ContractSummariser {

    protected List<String> findParagraphsContainingAnyOfTheseWords(List<String> inputParagraphs, List<String> words) {
        List<String> outputParagraphs = new ArrayList<>();
        for (String paragraph : inputParagraphs) {
            if (containsOneOrMore(paragraph, words)) {
                outputParagraphs.add(paragraph);
            }
        }
        return outputParagraphs;
    }

    private boolean containsOneOrMore(String paragraph, List<String> words) {
        for (String word : words) {
            if (paragraph.contains(word)) {
                return true; // return early, found one.  Possible future dev: continue searching e.g. to highlight all?
            }
        }
        return false;
    }
}