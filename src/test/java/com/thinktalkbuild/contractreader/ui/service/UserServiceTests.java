package com.thinktalkbuild.contractreader.ui.service;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.verify.VerificationTimes.exactly;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService service;

    private ClientAndServer mockServer;

    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(123);
    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test
    void whenCreateUser_shouldPostToAPI_WithOidToken() {

        mockServer.when(request().withPath("/user"))
                .respond(response().withBody(""));
        service.createUser("tokenabc");

        mockServer.verify(request()
                        .withPath("/user")
                        .withHeader(new Header("Authorization", "Bearer tokenabc")),
                exactly(1));

    }

}
