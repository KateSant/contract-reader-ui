package com.thinktalkbuild.contractreader.ui.controller;

import com.thinktalkbuild.contractreader.ui.model.*;
import com.thinktalkbuild.contractreader.ui.service.AddContractService;
import com.thinktalkbuild.contractreader.ui.service.AnalyserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.MultiValueMap;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddContractService addContractService;

    @Test
    void whenGetAddContractPagePage_withMockedAuth_thenReceiveSuccess()
            throws Exception {

        mvc.perform(get("/add-contract").with(oidcLogin()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Add")));
    }

    @Test
    void whenPostFormData_thenSuccess() throws Exception {

        doNothing().when(addContractService).postToAddEndpoint(anyString(), anyString());

        MvcResult result = mvc.perform(post("/add-contract")
                .param("name", "foo")
                .with(csrf())
                .with(oidcLogin()))
                .andExpect(status().is(200))
                .andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assertTrue(stringResult.contains("success"));

    }

}
