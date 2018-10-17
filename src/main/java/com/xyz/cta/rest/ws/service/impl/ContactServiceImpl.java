package com.xyz.cta.rest.ws.service.impl;

import com.xyz.cta.rest.ws.entity.Contact;
import com.xyz.cta.rest.ws.exception.ContactNotFoundException;
import com.xyz.cta.rest.ws.repository.ContactJpaRepository;
import com.xyz.cta.rest.ws.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactJpaRepository contactJpaRepository;

    @Override
    public List<Contact> findAll() {
        return contactJpaRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        Optional<Contact> optionalContact = contactJpaRepository.findById(id);
        if (optionalContact.isPresent()) {
            return optionalContact.get();
        } else {
            throw new ContactNotFoundException("There is no contact information for id - " + id);
        }
    }

    @Override
    public List<Contact> findByFirstNameLastNameAndZipCode(String firstName, String lastName, String zipCode) {
        return contactJpaRepository.findByFirstNameAndLastNameAndZipCode(firstName, lastName, zipCode);
    }

    @Override
    public List<Contact> findByFirstNameAndZip(String firstName, String zipCode) {
        return contactJpaRepository.findByFirstNameContainingAndZipCodeStartingWith(firstName, zipCode);
    }

    @Override
    public Contact save(Contact contact) {
        return contactJpaRepository.save(contact);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Contact> optionalContact = contactJpaRepository.findById(id);
        if (!optionalContact.isPresent()) {
            throw new ContactNotFoundException("Failed to delete Contact. No Contact found for id - " + id);
        } else {
            contactJpaRepository.delete(optionalContact.get());
        }
    }

}
