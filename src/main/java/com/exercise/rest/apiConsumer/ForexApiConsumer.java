package com.exercise.rest.apiConsumer;

import com.exercise.rest.exception.ForexServiceNotAvailableException;
import com.exercise.rest.model.SupportedCurrencies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Collections;
import java.util.Map;

/**
 *  The API consumer that calls data fixer api to get the current currency conversion rates.
 */
@Service
@Configuration
public class ForexApiConsumer {

    private static final String ACCESS_KEY = "access_key";

    private final RestTemplate restTemplate;

    @Value("${forex.apikey}")
    private  String forexApiKey;

    @Value("${forex.symbolendpoint}")
    private String forexApiSymbolEndpoint;

    @Value("${forex.latestendpoint}")
    private String forexApiLatestEndpoint;


    public ForexApiConsumer(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    private URI getUri(UriComponentsBuilder builder) {
        return builder.queryParam(ACCESS_KEY, forexApiKey).build().toUri();
    }

    private UriComponentsBuilder addQueryParams(UriComponentsBuilder builder,  String toCurrency) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap();
        queryParams.add("to", toCurrency);
        return builder.queryParams(queryParams);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    /**
     * Method that loads all the allowed currencies from the fixer api.
     *
     */
    public void loadSupportedCurrencies() {
        ResponseEntity<Map> responseEntity = restTemplate.exchange(getUri(UriComponentsBuilder.fromUriString(forexApiSymbolEndpoint)),
                HttpMethod.GET,
                new HttpEntity(getHeaders()),
                Map.class);
        if((Boolean) responseEntity.getBody().get("success")) {
            Map<String, String> result = (Map<String,  String>) responseEntity.getBody().get("symbols");
            SupportedCurrencies.getInstance().loadCurrencies(Collections.unmodifiableMap(result));
        } else {
            throw new ForexServiceNotAvailableException("Fetching all supported currencies resulted in Failure  ");
        }
    }

    /**
     * Method gets the latest conversion factor of the asked currency respective to EUR.
     *
     * @param ofCurrency currency for which conversion rate is asked.
     *
     * @return the conversion rate.
     */
    public BigDecimal latestRate(String ofCurrency) {
        BigDecimal conversionFactor = null;
        URI uri = getUri(addQueryParams(UriComponentsBuilder.fromUriString(forexApiLatestEndpoint),  ofCurrency));
        ResponseEntity<Map> responseEntity = restTemplate.exchange(uri,
                HttpMethod.GET,
                new HttpEntity(getHeaders()),
                Map.class);
        if((Boolean) responseEntity.getBody().get("success")) {
            Map<String, Double> currencyConversionRates = (Map<String,  Double>) responseEntity.getBody().get("rates");
            conversionFactor =  new BigDecimal(currencyConversionRates.get(ofCurrency));
        }else {
            throw new ForexServiceNotAvailableException("Fetching latest rate resulted in Failure  ", ofCurrency);
        }
        return conversionFactor;
    }
}
