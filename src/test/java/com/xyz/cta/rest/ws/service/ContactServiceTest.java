package com.xyz.cta.rest.ws.service;

import com.xyz.cta.rest.ws.entity.Contact;
import com.xyz.cta.rest.ws.exception.ContactNotFoundException;
import com.xyz.cta.rest.ws.repository.ContactJpaRepository;
import com.xyz.cta.rest.ws.service.impl.ContactServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ContactServiceTest {

    Contact contact1 = new Contact("Ram Bighorn", "rbhorn@rockies.com", "123 Business", "", "Ft Collins", "CO", "80521", "USA", "970-491-2345");
    Contact contact2 = new Contact("Prong Horn", "phorn@rockies.com", "123 Main", "", "Ft Collins", "CO", "80521", "USA", "970-491-3456");
    Long id1 = 10001L;
    @Mock
    private ContactJpaRepository contactJpaRepository;
    @InjectMocks
    private ContactServiceImpl contactService;

    @Test
    public void findAll() {

        when(contactJpaRepository.findAll())
                .thenReturn(Arrays.asList(contact1, contact2));

        List<Contact> resultList = contactService.findAll();

        assertEquals(resultList.get(0).getEmail(), "rbhorn@rockies.com");
        assertEquals(resultList.get(1).getEmail(), "phorn@rockies.com");

        assertEquals(2, resultList.size());

        verify(contactJpaRepository).findAll();
    }

    @Test
    public void findById() {

        when(contactJpaRepository.findById(anyLong()))
                .thenReturn(Optional.of(contact1));

        Contact contact = contactService.findById(id1);

        assertEquals(contact.getEmail(), "rbhorn@rockies.com");

        verify(contactJpaRepository).findById(id1);

    }

    @Test
    public void findByFirstNameLastNameAndZipCode() {

        when(contactJpaRepository.findByFirstNameAndLastNameAndZipCode(
                eq("Ram"),
                eq("Bighorn"),
                anyString()))
                .thenReturn(Arrays.asList(contact1));

        List<Contact> resultList = contactService
                .findByFirstNameLastNameAndZipCode("Ram", "Bighorn", "80521");

        assertEquals(1, resultList.size());
        assertEquals("rbhorn@rockies.com",
                resultList.get(0).getEmail());

        verify(contactJpaRepository)
                .findByFirstNameAndLastNameAndZipCode("Ram", "Bighorn", "80521");

    }


    @Test
    public void findByFirstNameLastNameAndZipCode_NoContact() {

        when(contactJpaRepository.findByFirstNameAndLastNameAndZipCode(
                anyString(), anyString(), anyString()))
                .thenReturn(Arrays.asList());

        List<Contact> resultList = contactService
                .findByFirstNameLastNameAndZipCode("Ram", "Bighorn", "80521");


        assertEquals(0, resultList.size());

        verify(contactJpaRepository)
                .findByFirstNameAndLastNameAndZipCode("Ram", "Bighorn", "80521");

    }

    @Test
    public void findByFirstNameAndZip() {

        when(contactJpaRepository.findByFirstNameContainingAndZipCodeStartingWith(
                anyString(), anyString()))
                .thenReturn(Arrays.asList(contact1));

        List<Contact> resultList = contactService
                .findByFirstNameAndZip("Ram", "80521");


        assertEquals(1, resultList.size());

        verify(contactJpaRepository)
                .findByFirstNameContainingAndZipCodeStartingWith("Ram", "80521");
    }

    @Test
    public void save() {

        when(contactJpaRepository.save(any(Contact.class))).thenReturn(contact1);

        //Contact contact = mock(Contact.class);
        Contact contact = contactService.save(contact1);

        assertEquals("Ram Bighorn", contact.getFullName());
        verify(contactJpaRepository).save(contact);
    }

   /* @Test
    public void save_mock() {
        Contact contact = mock(Contact.class);
        when(contact.getId()).thenReturn(id1);
        when(contact.getEmail()).thenReturn("test@test.com");
        when(contact.getPhone()).thenReturn("123-345-3456");
        when(contactJpaRepository.findById(id1)).thenReturn(Optional.of(contact));

        contactService.save(contact);

        assertEquals("test@test.com", contact.getEmail());
        verify(contactJpaRepository).save(contact);

    }*/

    @Test(expected = ContactNotFoundException.class)
    public void deleteById() {
        contactService.deleteById(100000l);
        verify(contactJpaRepository).deleteById(100000l);
    }
}