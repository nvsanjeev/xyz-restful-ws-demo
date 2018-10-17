package com.xyz.cta.rest.ws.controller;

import com.xyz.cta.rest.ws.entity.Contact;
import com.xyz.cta.rest.ws.service.CityStateZipCodeValidationService;
import com.xyz.cta.rest.ws.service.ContactService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ContactRestController.class)
public class ContactRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @MockBean
    private CityStateZipCodeValidationService cityStateZipCodeValidationService;

    private Contact contact1 = new Contact(
            "Ram Bighorn",
            "rbhorn@rockies.com",
            "123 Business",
            "",
            "Ft Collins",
            "CO",
            "80521",
            "USA",
            "970-491-2345");

    private Contact contact2 = new Contact(
            "Prong Horn",
            "phorn@rockies.com",
            "123 Main",
            "",
            "Ft Collins",
            "CO",
            "80521",
            "USA",
            "970-491-3456");


    @Test
    public void retrieveAllContacts() throws Exception {
        String expectedJsonResponse = "{\"_embedded\":{\"contacts\":[{\"id\":null,\"fullName\":\"Ram Bighorn\"," +
                "\"email\":\"rbhorn@rockies.com\",\"addressLine1\":\"123 Business\",\"addressLine2\":\"\"," +
                "\"city\":\"Ft Collins\",\"state\":\"CO\",\"zipCode\":\"80521\",\"country\":\"USA\"," +
                "\"phone\":\"970-491-2345\",\"_links\":{\"self\":{\"href\":\"http://localhost/contacts/{id}\",\"templated\":true}}}," +
                "{\"id\":null,\"fullName\":\"Prong Horn\",\"email\":\"phorn@rockies.com\",\"addressLine1\":\"123 Main\"," +
                "\"addressLine2\":\"\",\"city\":\"Ft Collins\",\"state\":\"CO\",\"zipCode\":\"80521\",\"country\":\"USA\"," +
                "\"phone\":\"970-491-3456\",\"_links\":{\"self\":{\"href\":\"http://localhost/contacts/{id}\",\"templated\":true}}}]}," +
                "\"_links\":{\"self\":{\"href\":\"http://localhost/contacts\"}}}";

        when(contactService.findAll()).thenReturn(Arrays.asList(contact1, contact2));

        RequestBuilder request = get("/contacts")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonResponse)).andReturn();
        ;

    }

    @Test
    public void retrieveContact() throws Exception {
        String expectedjsonResponse = "{\"id\":null,\"fullName\":\"Ram Bighorn\",\"email\":\"rbhorn@rockies.com\"," +
                "\"addressLine1\":\"123 Business\",\"addressLine2\":\"\",\"city\":\"Ft Collins\",\"state\":\"CO\"," +
                "\"zipCode\":\"80521\",\"country\":\"USA\",\"phone\":\"970-491-2345\"," +
                "\"_links\":{\"self\":{\"href\":\"http://localhost/contacts/{id}\",\"templated\":true}," +
                "\"all-contacts\":{\"href\":\"http://localhost/contacts\"}}}";

        when(contactService.findById(anyLong())).thenReturn(contact1);

        RequestBuilder request = get("/contacts/{id}", 10005)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(expectedjsonResponse))
                .andReturn();

    }

    @Test
    public void retrieveContactByFirstNameLastNameAndZipCode() throws Exception {

        String expectedjsonResponse = "{\"_embedded\":{\"contacts\":[{\"id\":null,\"fullName\":\"Ram Bighorn\",\"email\":\"rbhorn@rockies.com\"," +
                "\"addressLine1\":\"123 Business\",\"addressLine2\":\"\",\"city\":\"Ft Collins\",\"state\":\"CO\",\"zipCode\":\"80521\"," +
                "\"country\":\"USA\",\"phone\":\"970-491-2345\",\"_links\":{\"self\":{\"href\":\"http://localhost/contacts/{id}\"," +
                "\"templated\":true}}}]},\"_links\":{\"self\":{\"href\":\"http://localhost/contacts\"}}}";

        when(contactService.findByFirstNameLastNameAndZipCode(anyString(), anyString(), anyString()))
                .thenReturn(Arrays.asList(contact1));

        RequestBuilder request = get("/contacts/{firstName}/{lastName}/{zipCode}", "Ram", "Bighorn", "80521")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(expectedjsonResponse))
                .andReturn();

    }

    @Test
    public void retrieveContactByNameAndZipCode() throws Exception {
        String expectedjsonResponse = "{\"_embedded\":{\"contacts\":[{\"id\":null,\"fullName\":\"Ram Bighorn\"," +
                "\"email\":\"rbhorn@rockies.com\",\"addressLine1\":\"123 Business\",\"addressLine2\":\"\"," +
                "\"city\":\"Ft Collins\",\"state\":\"CO\",\"zipCode\":\"80521\",\"country\":\"USA\"," +
                "\"phone\":\"970-491-2345\",\"_links\":{\"self\":{\"href\":\"http://localhost/contacts/{id}\"," +
                "\"templated\":true}}}]},\"_links\":{\"self\":{\"href\":\"http://localhost/contacts\"}}}";

        when(contactService.findByFirstNameAndZip(anyString(), anyString()))
                .thenReturn(Arrays.asList(contact1));

        RequestBuilder request = get("/contacts/{firstName}/{zipCode}", "Ram", "80521")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(expectedjsonResponse))
                .andReturn();
    }

    @Test
    public void createContact() throws Exception {
        String inputJson = "{ " +
                "    \"fullName\": \"Ram Bighorn\", " +
                "    \"email\": \"rbhorn@rockies.com\", " +
                "    \"addressLine1\": \"123 Business\", " +
                "    \"addressLine2\": \"\", " +
                "    \"city\": \"Ft Collins\", " +
                "    \"state\": \"CO\", " +
                "    \"zipCode\": \"80521\", " +
                "    \"country\": \"USA\", " +
                "    \"phone\": \"970-491-2345\" " +
                "}";

        String expectedResponseJson = "{\"id\":null,\"fullName\":\"Ram Bighorn\",\"email\":\"rbhorn@rockies.com\"," +
                "\"addressLine1\":\"123 Business\",\"addressLine2\":\"\",\"city\":\"Ft Collins\",\"state\":\"CO\"," +
                "\"zipCode\":\"80521\",\"country\":\"USA\",\"phone\":\"970-491-2345\",\"_links\":" +
                "{\"self\":{\"href\":\"http://localhost/contacts/{id}\",\"templated\":true},\"all-contacts\":" +
                "{\"href\":\"http://localhost/contacts\"}}}";

        when(contactService.save(any(Contact.class))).thenReturn(contact1);

        when(cityStateZipCodeValidationService
                .isValidCityStateAndZipCode(anyString(), anyString(), anyString()))
                .thenReturn(true);

        RequestBuilder request = post("/contacts")
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResponseJson))
                .andReturn();
    }

    @Test
    public void updateContact() throws Exception {
        String inputJson = "{ " +
                "    \"id\": 10005, " +
                "    \"fullName\": \"Ram Bighorn\", " +
                "    \"email\": \"rbhorn@rockies.com\", " +
                "    \"addressLine1\": \"123 Business\", " +
                "    \"addressLine2\": \"\", " +
                "    \"city\": \"Ft Collins\", " +
                "    \"state\": \"CO\", " +
                "    \"zipCode\": \"80521\", " +
                "    \"country\": \"USA\", " +
                "    \"phone\": \"970-491-2345\"  " +
                "}";

        String expectedResponseJson = "{\"id\":null,\"fullName\":\"Ram Bighorn\",\"email\":\"rbhorn@rockies.com\"," +
                "\"addressLine1\":\"123 Business\",\"addressLine2\":\"\",\"city\":\"Ft Collins\",\"state\":\"CO\"," +
                "\"zipCode\":\"80521\",\"country\":\"USA\",\"phone\":\"970-491-2345\",\"_links\":{\"self\":{\"href\":" +
                "\"http://localhost/contacts/{id}\",\"templated\":true},\"all-contacts\":" +
                "{\"href\":\"http://localhost/contacts\"}}}";

        when(contactService.save(any(Contact.class))).thenReturn(contact1);

        when(cityStateZipCodeValidationService
                .isValidCityStateAndZipCode(anyString(), anyString(), anyString()))
                .thenReturn(true);

        RequestBuilder request = put("/contacts")
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson))
                .andReturn();

    }

    @Test
    public void deleteContact() throws Exception {

        RequestBuilder request = delete("/contacts/{id}", "10001")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }
}