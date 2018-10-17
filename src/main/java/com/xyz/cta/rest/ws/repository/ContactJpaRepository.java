package com.xyz.cta.rest.ws.repository;

import com.xyz.cta.rest.ws.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ContactJpaRepository extends JpaRepository<Contact, Long> {

    //List<Contact> findByFirstNameContainingAndZipCodeContaining(String firstName, String zipCode);
    List<Contact> findByFirstNameContainingAndZipCodeStartingWith(String firstName, String zipCode);

    List<Contact> findByFirstNameAndLastNameAndZipCode(String firstName, String lastName, String zipCode);
}
