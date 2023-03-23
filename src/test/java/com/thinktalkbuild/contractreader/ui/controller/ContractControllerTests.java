package com.thinktalkbuild.contractreader.ui.controller;

import com.thinktalkbuild.contractreader.ui.service.ContractService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContractControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ContractService addContractService;

    @Test
    void whenGetAddContractPage_withMockedAuth_thenReceiveSuccess()
            throws Exception {

        mvc.perform(get("/add-contract").with(oidcLogin()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void whenPostFormData_thenSuccess() throws Exception {

        doNothing().when(addContractService).postToAddEndpoint(anyObject(), anyString());

        MvcResult result = mvc.perform(post("/add-contract")
                .param("name", "foo")
                .param("startDate", "2023-03-01")
                .with(csrf())
                .with(oidcLogin()))
                .andExpect(status().is(200))
                .andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assertTrue(stringResult.contains("success"));

    }

    @Test
    void whenGetMyContractsPage_thenReceiveSuccess()
            throws Exception {

        mvc.perform(get("/my-contracts").with(oidcLogin()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

}
