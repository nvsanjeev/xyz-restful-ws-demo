package com.xyz.cta.rest.ws.repository;

import com.xyz.cta.rest.ws.entity.Contact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ContactJpaRepositoryTest {


    @Autowired
    private ContactJpaRepository jpaRepository;

    Contact contact1 = new Contact(
            "Ram Bighorn",
            "rbhorn@rockies.com",
            "123 Business",
            "",
            "Ft Collins",
            "CO",
            "80521",
            "USA",
            "970-491-2345");

    @Test
    public void findAll(){
        List<Contact> resultList = jpaRepository.findAll();
        assertEquals(6, resultList.size());
    }
    @Test
    public void findByFirstNameContainingAndZipCodeStartingWith() {
        List<Contact> resultList =
                jpaRepository.findByFirstNameContainingAndZipCodeStartingWith("CAT", "60107");

        assertEquals(1, resultList.size());
        assertEquals("CAT", resultList.get(0).getFirstName());
    }

    @Test
    public void findByFirstNameAndLastNameAndZipCode() {
        List<Contact> resultList =
                jpaRepository.findByFirstNameAndLastNameAndZipCode("CAT", "SAVANAH", "60107");

        assertEquals(1, resultList.size());
        assertEquals("CAT", resultList.get(0).getFirstName());
    }

    @Test
    public void save(){
        Contact contact = jpaRepository.save(contact1);
        assertNotNull(contact.getId());
    }


    @Test
    public void delete(){
        Contact contact = jpaRepository.save(contact1);
        assertNotNull(contact.getId());
        jpaRepository.delete(contact);
    }
}