package com.xyz.cta.rest.ws.validator;

import com.xyz.cta.rest.ws.entity.Contact;
import com.xyz.cta.rest.ws.service.CityStateZipCodeValidationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ContactZipCodeValidator implements ConstraintValidator<ValidZipCode, Contact> {

    @Autowired
    CityStateZipCodeValidationService validationService;

    @Override
    public void initialize(ValidZipCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(Contact contact, ConstraintValidatorContext constraintValidatorContext) {

        return validationService.isValidCityStateAndZipCode(
                contact.getCity(), contact.getState(), contact.getZipCode());
    }
}
