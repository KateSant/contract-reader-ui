package com.thinktalkbuild.contractreader.ui.service;

import com.thinktalkbuild.contractreader.ui.model.analysis.Analysis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class AnalyserService extends SecureRestClient{

    @Value("${contract-reader.engine.url}")
    private String apiUrl;

    @Value("${contract-reader.engine.endpoint.analyse}")
    private String endpoint;

    public Analysis postToAnalysisEngine(MultipartFile file, String idToken) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String url = apiUrl + endpoint;
        log.info("Posting to engine [{}] with token [{}]", url, idToken);
        RestTemplate restTemplate = restTemplateWithAuthHeader(idToken);
        ResponseEntity<Analysis> response = restTemplate.postForEntity(url, requestEntity, Analysis.class);
        if (response.getStatusCode().isError()){
            log.error("Bad response from engine {}", response);
            throw new Exception("Bad response from engine");
        }
        return response.getBody();
    }


}
