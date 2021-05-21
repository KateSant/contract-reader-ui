package com.thinktalkbuild.contractreader.service;


import com.thinktalkbuild.contractreader.model.Obligation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author kate
 */
public class ObligationsFinderTests {

    private ObligationsFinder obligationsFinder= new ObligationsFinder();

    static String PARAGRAPH_WITH_3_OBLIGATIONS = "This is the first sentence, it says that The Supplier must sit down.  This is the second sentence, it says that a Supplier must also stand up.  " +
            "This is the third sentence in the paragraph, it says that The Customer shall do a hand stand.";

    static List<String> LIST_OF_PARAGRAPHS = new ArrayList<>(List.of(
            PARAGRAPH_WITH_3_OBLIGATIONS,
            "This is the second paragraph, it says that The Customer shall stand up."));

    static String SENTENCE_WITH_2_OBLGATIONS="The Customer must pay the money and the Supplier must provide the service.";

    @Test
    void testFindObligations_finds_must() {

        List<Obligation> obligations =  obligationsFinder.findObligations(PARAGRAPH_WITH_3_OBLIGATIONS);
        assertTrue(obligations.size()>0);
        Obligation firstSentence = obligations.get(0);
        assertEquals(firstSentence.getWholeSentence(), "This is the first sentence, it says that The Supplier must sit down.");
        assertEquals(firstSentence.getObligingVerb(), "must");
        assertEquals(firstSentence.getParty(), "Supplier");
        assertEquals(firstSentence.getAction(), "sit down");
    }

    @Test
    void testFindObligations_finds_shall() {
        List<Obligation> obligations =  obligationsFinder.findObligations(PARAGRAPH_WITH_3_OBLIGATIONS);
        assertTrue(obligations.size()>0);
        assertTrue(obligations.stream().anyMatch(obl -> obl.getObligingVerb().equals("shall")));
    }

    @Test
    void testFindObligations_finds_multiple_obligaitons_in_para() {
        List<Obligation> obligations =  obligationsFinder.findObligations(PARAGRAPH_WITH_3_OBLIGATIONS);
        assertEquals(obligations.size(),3);
    }

    @Test
    void testFindObligations_finds_multiple_obligations_in_sentence() {
        List<Obligation> obligations =  obligationsFinder.findObligations(SENTENCE_WITH_2_OBLGATIONS);
        assertEquals(obligations.size(),3);
    }


    @Test
    void testFindsObligationsInParagraphs() {
        List<Obligation> obligations = obligationsFinder.findObligations(LIST_OF_PARAGRAPHS);
        assertEquals(obligations.size(),4);
    }


}
