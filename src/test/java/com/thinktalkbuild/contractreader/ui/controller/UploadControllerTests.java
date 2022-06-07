package com.thinktalkbuild.contractreader.ui.controller;

import com.thinktalkbuild.contractreader.ui.model.*;
import com.thinktalkbuild.contractreader.ui.service.AnalyserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.ByteArrayInputStream;
import java.time.Period;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UploadControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AnalyserService mockAnalyserService;

    @Test
    void whenGetUploadPage_thenReceiveSuccess()
            throws Exception {

        mvc.perform(get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Upload")));
    }

    @Test
    void whenPostAFile_thenSeeAnalysisPage() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile("file", "FileName.docx","multipart/form-data", new ByteArrayInputStream("foo".getBytes()));

        ContractSummary s = new ContractSummary();
        s.addSection(new ContractSummarySection("Warranties and indemnities", Collections.singletonList("Kate warrants that apples are pears")));
        Analysis a = new Analysis();
        a.setSummary(s);

        Map<String, List<Obligation>> mockSortedObligations = new HashMap<>();
        ObligationsByParty obligations = new ObligationsByParty(mockSortedObligations);
        a.setObligationsByParty(obligations);


        when(mockAnalyserService.postToAnalysisEngine(mockFile)).thenReturn(a);

        MvcResult result = mvc.perform(multipart("/upload-file")
                .file(mockFile))
                .andExpect(status().is(200))
                .andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assertTrue(stringResult.contains("<h2 class=\"subtitle\">Warranties and indemnities</h2>"));
        assertTrue(stringResult.contains("apples are pears"));

    }

    @Test
    void whenEngineReturnsError_thenShowErrorPageInUI() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile("file", "FileName.docx","multipart/form-data", new ByteArrayInputStream("foo".getBytes()));

        when(mockAnalyserService.postToAnalysisEngine(mockFile)).thenThrow(new Exception());

        MvcResult result = mvc.perform(multipart("/upload-file")
                .file(mockFile))
                .andExpect(status().is(200))
                .andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assertTrue(stringResult.contains("Failed to process file"));

    }

}
