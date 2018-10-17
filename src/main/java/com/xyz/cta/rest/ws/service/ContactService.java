package com.xyz.cta.rest.ws.service;

import com.xyz.cta.rest.ws.entity.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> findAll();

    Contact findById(Long id);

    List<Contact> findByFirstNameLastNameAndZipCode(String firstName, String lastName, String zipCode);

    List<Contact> findByFirstNameAndZip(String firstName, String zipCode);

    Contact save(Contact contact);

    void deleteById(Long id);
}
