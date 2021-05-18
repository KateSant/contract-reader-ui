package com.thinktalkbuild.contractreader.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;




/**
 *
 * @author kate
 */
public class ContractSummariserTests {
    
    
   private ContractSummariser summariser = new ContractSummariser();

   @Test
   void testFindParagraphsContainingTheseWords_findsSingleParaSingleWord() {
       
       List<String> inputParagraphs = new ArrayList<>();
       inputParagraphs.add("This is the first paragraph it contains the word apple.");
       inputParagraphs.add("This is the first paragraph it contains the word banana.");
       
       List<String> output = summariser.findParagraphsContainingTheseWords(inputParagraphs, Collections.singletonList("banana"));
       assertEquals(output.size(),1);
       assertTrue(output.get(0).contains("contains the word banana"));
       
       
   }
   
   
}
