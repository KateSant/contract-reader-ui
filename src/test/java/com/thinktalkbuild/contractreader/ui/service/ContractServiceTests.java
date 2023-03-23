package com.thinktalkbuild.contractreader.ui.service;

import com.thinktalkbuild.contractreader.ui.model.dto.ContractMetadata;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.verify.VerificationTimes.exactly;
import static org.springframework.http.HttpMethod.POST;

@SpringBootTest
public class ContractServiceTests {

    @Autowired
    private ContractService service;

    private ClientAndServer mockServer;

    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(9090);
    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test
    void whenCallPostToEndpoint_thenSendsPost() throws Exception {

        mockServer.when(request().withPath("/contract"))
                .respond(response().withBody(""));

        service.postToAddEndpoint(new ContractMetadata(), "sometoken");

        mockServer.verify(request()
                    .withPath("/contract")
                        .withMethod("POST"),
                exactly(1));

    }

    @Test
    void whenCallGetToEndpoint_thenSendsGet() throws Exception {

        mockServer.when(request().withPath("/contract"))
                .respond(response().withBody("[]"));

        service.getContractMetadata("sometoken");

        mockServer.verify(request()
                        .withPath("/contract")
                        .withMethod("GET"),
                exactly(1));

    }

}
