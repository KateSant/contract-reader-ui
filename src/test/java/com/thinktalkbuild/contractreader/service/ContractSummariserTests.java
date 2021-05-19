package com.thinktalkbuild.contractreader.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.thinktalkbuild.contractreader.model.ContractSection;
import com.thinktalkbuild.contractreader.model.DefaultSearchConfig;
import com.thinktalkbuild.contractreader.model.SearchCriteria;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * @author kate
 */
public class ContractSummariserTests {


    private ContractSummariser summariser;

    @BeforeEach
    void setup(){
        summariser = new ContractSummariser(new DefaultSearchConfig(), new Highlighter());
    }

    static List<String> DUMMY_INPUT_DATA = new ArrayList<>(List.of(
            "This is the first paragraph, it contains the word apple.",
            "This is the second paragraph, it contains the words apple and banana and cherry.",
            "This is the third paragraph, it contains no fruit words since it is about dragons.",
            "This is the fourth paragraph, it contains the word Elderberry capitalised."));
    

    @Test
    void testFindParagraphsContainingTheseWords_findsSingleParaContainingSingleWord() {
        List<String> output = summariser.findParagraphsContainingAnyOfTheseWords(DUMMY_INPUT_DATA, Collections.singletonList("banana"));
        assertEquals(output.size(), 1);
        assertTrue(output.get(0).contains("second paragraph"));
    }

    @Test
    void testFindParagraphsContainingTheseWords_findsTwoParasContainingSingleWord() {

        List<String> output = summariser.findParagraphsContainingAnyOfTheseWords(DUMMY_INPUT_DATA, Collections.singletonList("apple"));
        assertEquals(output.size(), 2);
        assertTrue(output.get(0).contains("first paragraph"));
        assertTrue(output.get(1).contains("second paragraph"));
    }

    @Test
    void testFindParagraphsContainingTheseWords_findsSingleParaContainingTwoWordsButDoesNotDuplicate() {

        List<String> output = summariser.findParagraphsContainingAnyOfTheseWords(DUMMY_INPUT_DATA, Arrays.asList(new String[]{"no", "dragons"}));
        assertEquals(output.size(), 1);
        assertTrue(output.get(0).contains("third paragraph"));
    }

    @Test
    void testFindParagraphsContainingTheseWords_isCaseInsensitive() {
        List<String> output = summariser.findParagraphsContainingAnyOfTheseWords(DUMMY_INPUT_DATA, Collections.singletonList("elderberry"));
        assertEquals(output.size(), 1);
        assertTrue(output.get(0).contains("fourth paragraph"));
    }

    @Test
    void testGenerateSummarySection_hasCorrectParagraphs(){
        SearchCriteria mockCriteria = new SearchCriteria("", Collections.singletonList("apple"));
        ContractSection result = summariser.generateSummarySection(DUMMY_INPUT_DATA, mockCriteria);
        assertEquals(result.getResultsParagraphs().size(), 2);
    }

    @Test
    void testGenerateSummarySection_hasTitle(){
        SearchCriteria mockCriteria = new SearchCriteria("Mock Title", Collections.emptyList());
        ContractSection result = summariser.generateSummarySection(DUMMY_INPUT_DATA, mockCriteria);
        assertEquals(result.getTitle(), "Mock Title");
    }

}
