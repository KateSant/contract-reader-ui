package com.thinktalkbuild.contractreader.service;

import com.thinktalkbuild.contractreader.model.ContractSummary;
import com.thinktalkbuild.contractreader.model.ContractSummarySection;
import com.thinktalkbuild.contractreader.model.Obligation;
import com.thinktalkbuild.contractreader.model.config.ContractSummaryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author kate
 */
@Service
public class ObligationsFinder {


    protected List<Obligation> findObligations(List<String> inputParagraphs) {
        List<Obligation> obligations = new ArrayList<>();
        return obligations;
    }


}