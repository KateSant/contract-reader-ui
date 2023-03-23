package com.thinktalkbuild.contractreader.ui.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thinktalkbuild.contractreader.ui.model.dto.ContractMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 *
 * @author kate
 */
@Service
@Slf4j
public class ContractService extends SecureRestClient{

    @Value("${contract-reader.engine.url}")
    private String apiUrl;

    @Value("${contract-reader.engine.endpoint.contract}")
    private String contractEndpoint;

    public void postToAddEndpoint(ContractMetadata contract, String idToken) throws Exception {
        String url = apiUrl + contractEndpoint;
        RestTemplate restTemplate = restTemplateWithAuthHeader(idToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String json = mapper.writeValueAsString(contract);
        log.info("Posting to endpoint [{}] json [{}] with token [{}]", url, json, idToken);

        HttpEntity requestEntity = new HttpEntity<>(json, headers);
        ResponseEntity response = restTemplate.postForEntity(url, requestEntity, String.class);

        //.. do something with response
    }

    public void getContractMetadata(String idToken) throws Exception {
        String url = apiUrl + contractEndpoint;
        RestTemplate restTemplate = restTemplateWithAuthHeader(idToken);
        log.info("Getting from endpoint [{}] with token [{}]", url, idToken);
    }

}
