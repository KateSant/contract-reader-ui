package com.thinktalkbuild.contractreader.service;


import com.thinktalkbuild.contractreader.model.ContractSummarySection;
import com.thinktalkbuild.contractreader.model.Obligation;
import com.thinktalkbuild.contractreader.model.config.ContractSummaryConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author kate
 */
public class ObligationsFinderTests {

    private ObligationsFinder obligationsFinder= new ObligationsFinder();

    static String DUMMY_PARAGRAPH = "This is the first sentence, it says that The Supplier must sit down.  This is the second sentence, it says that a Supplier must also stand up.  " +
            "This is the third sentence in the paragraph, it says that The Customer shall do a hand stand.";

    static List<String> DUMMY_PARAGRAPHS_LIST = new ArrayList<>(List.of(
            DUMMY_PARAGRAPH,
            "This is the second paragraph, it says that The Customer shall stand up."));

    @Test
    void testFindObligations_finds_must() {

        List<Obligation> obligations =  obligationsFinder.findObligations(DUMMY_PARAGRAPH);
        assertTrue(obligations.size()>0);
        Obligation firstSentence = obligations.get(0);
        assertEquals(firstSentence.getWholeSentence(), "This is the first sentence, it says that The Supplier must sit down.");
        assertEquals(firstSentence.getObligingVerb(), "must");
        assertEquals(firstSentence.getParty(), "Supplier");
    }



    @Test
    void testFindsObligationsInParagraphs() {
        //List<Obligation> output = obligationsFinder.findObligations(DUMMY_PARAGRAPHS_LIST);
        //assertTrue(output.get(0).getWholeSentence().contains("sit down."));
    }


}
