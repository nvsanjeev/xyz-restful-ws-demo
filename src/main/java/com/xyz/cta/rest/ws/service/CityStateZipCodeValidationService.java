package com.xyz.cta.rest.ws.service;

public interface CityStateZipCodeValidationService {

    boolean isValidCityStateAndZipCode(String city, String state, String zipCode);
}
