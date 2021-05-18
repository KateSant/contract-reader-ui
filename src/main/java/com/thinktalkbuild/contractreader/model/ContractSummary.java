package com.thinktalkbuild.contractreader.model;

import java.util.ArrayList;
import java.util.List;

public class ContractSummary {

    // todo - some metadata here? e.g. filename, date of processing?

    private List<ContractSection> sections = new ArrayList<>();
    public void addSection(ContractSection s){
        sections.add(s);
    }

    public List<ContractSection> getSections() {
        return sections;
    }
}
