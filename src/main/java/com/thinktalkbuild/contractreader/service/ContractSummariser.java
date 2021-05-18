package com.thinktalkbuild.contractreader.service;

import com.thinktalkbuild.contractreader.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author kate
 */
@Service
public class ContractSummariser {

    private SearchConfig config;

    public ContractSummariser(){
        config = new DefaultSearchConfig();
    }
    public ContractSummariser(SearchConfig config){
        this.config = config;
    }

    public ContractSummary summarise(List<String> inputParagraphs){

        ContractSummary summary = new ContractSummary();
        config.getSearchCriteria().stream().forEach(sc -> {
            ContractSection section = generateSummarySection(inputParagraphs, sc);
            summary.addSection(section);
        });
        return summary;

    }
    public ContractSection generateSummarySection(List<String> inputParagraphs, SearchCriteria searchCriteria){
        List<String> outputParagraphs = findParagraphsContainingAnyOfTheseWords(inputParagraphs, searchCriteria.getWordsToSearchFor());
        return new ContractSection(searchCriteria.getTitle(), outputParagraphs);
    }

    protected List<String> findParagraphsContainingAnyOfTheseWords(List<String> inputParagraphs, List<String> words) {
        return inputParagraphs.stream().filter (
                 para -> containsOneOrMore(para, words))
                .collect(Collectors.toList());
    }

    private boolean containsOneOrMore(String paragraph, List<String> words) {
        return words.stream().anyMatch(word -> paragraph.contains(word));// return early, found one.  Possible future dev: continue searching e.g. to highlight all?
    }
}