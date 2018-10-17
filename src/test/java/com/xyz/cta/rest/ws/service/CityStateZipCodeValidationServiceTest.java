package com.xyz.cta.rest.ws.service;

import com.xyz.cta.rest.ws.service.impl.CityStateZipCodeValidationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CityStateZipCodeValidationServiceTest {

    @Mock
    private RestTemplate restTemplate;


    private
    @InjectMocks
    CityStateZipCodeValidationServiceImpl cityStateZipCodeValidationService;

    @Test
    public void isValidCityStateAndZipCode() {
        String response = "{\"zip_codes\": [\"60169\", \"60179\", \"60192\", \"60010\", \"60067\", \"60173\", \"60195\", \"60196\"]}";

        when(restTemplate.getForObject(anyString(), eq(String.class) )).thenReturn(response);

        boolean result = cityStateZipCodeValidationService.isValidCityStateAndZipCode("Hoffman Estates", "IL", "60169");

        assertTrue(result);
    }

    @Test
    public void isValidCityStateAndZipCode_Failure() {
        String response = "{\"zip_codes\": [\"60169\", \"60179\", \"60192\", \"60010\", \"60067\", \"60173\", \"60195\", \"60196\"]}";

        when(restTemplate.getForObject(anyString(), eq(String.class) )).thenReturn(response);

        boolean result = cityStateZipCodeValidationService.isValidCityStateAndZipCode("Hoffman Estates", "IL", "34567");
        assertFalse(result);
    }
}