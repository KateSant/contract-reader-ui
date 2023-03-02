package com.thinktalkbuild.contractreader.ui.model.analysis;

import lombok.Data;

import java.util.List;

@Data
public class Analysis {

    String fileName;
    ContractSummary summary;
    ObligationsByParty obligationsByParty;
    List<Duration> durations;
}
