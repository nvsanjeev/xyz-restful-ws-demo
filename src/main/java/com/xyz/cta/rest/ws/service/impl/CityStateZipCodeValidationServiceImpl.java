package com.xyz.cta.rest.ws.service.impl;

import com.xyz.cta.rest.ws.service.CityStateZipCodeValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
public class CityStateZipCodeValidationServiceImpl implements CityStateZipCodeValidationService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${zipcode.api.base.url}")
    private String zipcodeApiBeaseurl = "https://www.zipcodeapi.com/rest/I2pqvu6X0o6oYCvfcknMEx0hXdtrvK3px2zyl9kHr8WBkMr8HFvSO0vjFQM1JbcS/city-zips.json/{city}/{state}";

    @Override
    @Transactional
    public boolean isValidCityStateAndZipCode(String city, String state, String zipCode) {

        //== URI (URL) parameters ==
        Map<String, String> uriParams = new HashMap<String, String>();
        uriParams.put("city", city);
        uriParams.put("state", state);

        String url = UriComponentsBuilder
                .fromUriString(zipcodeApiBeaseurl)
                .buildAndExpand(uriParams)
                .toUriString();

        String zipCodes = restTemplate.getForObject(url, String.class);

        if (StringUtils.isEmpty(zipCodes)) {
            return false;
        }
        return zipCodes.contains(zipCode);
    }
}
