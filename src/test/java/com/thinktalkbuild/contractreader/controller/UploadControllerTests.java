package com.thinktalkbuild.contractreader.controller;

import com.thinktalkbuild.contractreader.model.ContractSummary;
import com.thinktalkbuild.contractreader.model.ContractSummarySection;
import com.thinktalkbuild.contractreader.model.Obligation;
import com.thinktalkbuild.contractreader.model.ObligationsByParty;
import com.thinktalkbuild.contractreader.service.ContractSummariser;
import com.thinktalkbuild.contractreader.service.ObligationsFinder;
import com.thinktalkbuild.contractreader.service.TestUtils;
import com.thinktalkbuild.contractreader.service.WordDocReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UploadControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WordDocReader reader;

    @MockBean
    private ContractSummariser summariser;

    @MockBean
    private ObligationsFinder obligationsFinder;

    @Test
    void whenGetUploadPage_thenReceiveSuccess()
            throws Exception {

        mvc.perform(get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Upload")));

    }

    @Test
    void whenPostAFile_thenReceiveAnalysis() throws Exception {
        MockMultipartFile mockFile = new TestUtils().dummyWordDoc("WordDocWithLinesAndParagraphs.docx");
        ContractSummary mockContractSummary = new ContractSummary();
        mockContractSummary.addSection(new ContractSummarySection("Dummy section title", Collections.singletonList("Dummy section")));

        Obligation mockObligation = new Obligation();
        mockObligation.setParty("The Supplier");
        mockObligation.setContextHighlighted("The Supplier must do stuff");
        Map<String, List<Obligation>> mockSortedObligations = new HashMap<>();
        mockSortedObligations.put("SUPPLIER", Collections.singletonList(mockObligation));

        ObligationsByParty obligations = new ObligationsByParty(mockSortedObligations);

        when(reader.extractTextFromFile(mockFile)).thenReturn("Dummy raw text");
        when(reader.parseParagraphs(anyString())).thenReturn(Collections.singletonList("Dummy raw paragraph"));
        when(summariser.summarise(anyList())).thenReturn(mockContractSummary);
        when(obligationsFinder.findAndSortObligations(anyList())).thenReturn(obligations);

        MvcResult result = mvc.perform(multipart("/upload-file")
                .file(mockFile))
                .andExpect(status().is(200))
                .andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assertTrue(stringResult.contains("<h2 class=\"subtitle\">Dummy section title</h2>"));

        assertTrue(stringResult.contains("<p>Dummy section</p>"));
        assertTrue(stringResult.contains("<h2 class=\"subtitle\">SUPPLIER</h2>"));
        assertTrue(stringResult.contains("The Supplier must do stuff"));


    }
}
