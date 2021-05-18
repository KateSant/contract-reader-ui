package com.thinktalkbuild.contractreader.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kate
 */
public class Summary {
    private List<SummaryItem> summaryItems = new ArrayList<>();

    public Summary(List<SummaryItem> summaryItems) {
        this.summaryItems = summaryItems;
    }
    
    
    

    public List<SummaryItem> getSummaryItems() {
        return summaryItems;
    }

    public void addSummaryItem(SummaryItem item) {
        this.summaryItems.add(item);
    }
    
    
    
    
}
