package com.thinktalkbuild.contractreader.model.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "obligations")
public class ObligationsConfig {

    private List<String> obligingVerbs;

    public List<String> getObligingVerbs() {
        return obligingVerbs;
    }

    public void setObligingVerbs(List<String> obligingVerbs) {

        this.obligingVerbs = obligingVerbs;
    }

}