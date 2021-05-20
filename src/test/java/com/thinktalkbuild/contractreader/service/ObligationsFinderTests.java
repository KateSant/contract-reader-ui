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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @author kate
 */
public class ObligationsFinderTests {


    private ObligationsFinder obligationsFinder;

    @BeforeEach
    void setup(){ obligationsFinder= new ObligationsFinder();
    }

    static List<String> DUMMY_INPUT_DATA = new ArrayList<>(List.of(
            "This is the first paragraph, it says that The Supplier must sit down.",
            "This is the second paragraph, it says that The Customer shall stand up."));

    @Test
    void givenMust_findsObligationSentence() {
        List<Obligation> output = obligationsFinder.findObligations(DUMMY_INPUT_DATA);
        assertTrue(output.get(0).getWholeSentence().contains("sit down."));
    }


}
