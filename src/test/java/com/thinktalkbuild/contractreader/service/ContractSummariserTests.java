package com.thinktalkbuild.contractreader.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.thinktalkbuild.contractreader.model.ContractSection;
import com.thinktalkbuild.contractreader.model.SearchCriteria;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


/**
 * @author kate
 */
public class ContractSummariserTests {


    private ContractSummariser summariser = new ContractSummariser();

    private static List<String> inputParagraphs = new ArrayList<>();

    @BeforeAll
    static void setup() {
        inputParagraphs.add("This is the first paragraph, it contains the word apple.");
        inputParagraphs.add("This is the second paragraph, it contains the words apple and banana.");
        inputParagraphs.add("This is the third paragraph, it contains no fruit words since it is about dragons.");
    }


    @Test
    void testFindParagraphsContainingTheseWords_findsSingleParaContainingSingleWord() {
        List<String> output = summariser.findParagraphsContainingAnyOfTheseWords(inputParagraphs, Collections.singletonList("banana"));
        assertEquals(output.size(), 1);
        assertTrue(output.get(0).contains("second paragraph"));
    }

    @Test
    void testFindParagraphsContainingTheseWords_findsTwoParasContainingSingleWord() {

        List<String> output = summariser.findParagraphsContainingAnyOfTheseWords(inputParagraphs, Collections.singletonList("apple"));
        assertEquals(output.size(), 2);
        assertTrue(output.get(0).contains("first paragraph"));
        assertTrue(output.get(1).contains("second paragraph"));

    }

    @Test
    void testFindParagraphsContainingTheseWords_findsSingleParaContainingTwoWordsButDoesNotDuplicate() {

        List<String> output = summariser.findParagraphsContainingAnyOfTheseWords(inputParagraphs, Arrays.asList(new String[]{"no", "dragons"}));
        assertEquals(output.size(), 1);
        assertTrue(output.get(0).contains("third paragraph"));
    }

    @Test
    void testGenerateSummarySection_hasCorrectParagraphs(){
        SearchCriteria mockCriteria = new SearchCriteria("", Collections.singletonList("apple"));
        ContractSection result = summariser.generateSummarySection(inputParagraphs, mockCriteria);
        assertEquals(result.getResultsParagraphs().size(), 2);
    }

    @Test
    void testGenerateSummarySection_hasTitle(){
        SearchCriteria mockCriteria = new SearchCriteria("Mock Title", Collections.emptyList());
        ContractSection result = summariser.generateSummarySection(inputParagraphs, mockCriteria);
        assertEquals(result.getTitle(), "Mock Title");
    }

}
