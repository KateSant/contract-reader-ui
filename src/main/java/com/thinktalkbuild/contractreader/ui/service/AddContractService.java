package com.thinktalkbuild.contractreader.ui.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinktalkbuild.contractreader.ui.model.analysis.Analysis;
import com.thinktalkbuild.contractreader.ui.model.dto.ContractMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


/**
 *
 * @author kate
 */
@Service
@Slf4j
public class AddContractService extends SecureRestClient{

    @Value("${contract-reader.engine.url}")
    private String apiUrl;

    @Value("${contract-reader.engine.endpoint.add-contract}")
    private String endpoint;

    public void postToAddEndpoint(ContractMetadata contract, String idToken) throws Exception {
        String url = apiUrl + endpoint;
        RestTemplate restTemplate = restTemplateWithAuthHeader(idToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(contract);
        log.info("Posting to endpoint [{}] json [{}] with token [{}]", url, json, idToken);

        HttpEntity requestEntity = new HttpEntity<>(json, headers);
        ResponseEntity response = restTemplate.postForEntity(url, requestEntity, String.class);

        //.. do something with response
    }


}
