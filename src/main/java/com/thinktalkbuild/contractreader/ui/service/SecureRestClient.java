package com.thinktalkbuild.contractreader.ui.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class SecureRestClient {

    protected RestTemplate restTemplateWithAuthHeader(String token){
        return new RestTemplateBuilder(rt-> rt.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer "+token);
            return execution.execute(request, body);
        })).build();
    }
}
