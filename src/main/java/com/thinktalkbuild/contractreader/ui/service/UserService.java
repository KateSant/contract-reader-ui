package com.thinktalkbuild.contractreader.ui.service;

import com.thinktalkbuild.contractreader.ui.model.Analysis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author kate
 */
@Service
@Slf4j
public class UserService extends SecureRestClient {

    @Value("${contract-reader.engine.url}")
    private String apiUrl;

    @Value("${contract-reader.engine.endpoint.user}")
    private String endpoint;

    public void createUser(String idToken){
        String url = apiUrl + endpoint;
        log.info("Posting to endpoint [{}] with token [{}]", url, idToken);
        RestTemplate restTemplate = restTemplateWithAuthHeader(idToken);
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        log.info("Response from user endpoint = [{}] [{}]", response, response.getStatusCode());
    }



}
