package com.exercise.rest.apiConsumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Configuration
public class ForexApiConsumer {

    private final RestTemplate restTemplate;

    @Value("${forex.apikey}")
    private  String forexApiKey;

    public ForexApiConsumer(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getAllSupportedCurrencies() {
     //   restTemplate.get
        return "";
    }


}
