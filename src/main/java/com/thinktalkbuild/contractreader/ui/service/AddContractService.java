package com.thinktalkbuild.contractreader.ui.service;

import com.thinktalkbuild.contractreader.ui.model.dto.ContractMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


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

    public void postToAddEndpoint(ContractMetadata contract, String idToken) {
        //STUB
        String url = apiUrl + endpoint;
        log.info("Posting to endpoint [{}] with token [{}]", url, idToken);


    }


}
