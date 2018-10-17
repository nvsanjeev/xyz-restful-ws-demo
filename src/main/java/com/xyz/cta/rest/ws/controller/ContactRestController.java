package com.xyz.cta.rest.ws.controller;

import com.xyz.cta.rest.ws.entity.Contact;
import com.xyz.cta.rest.ws.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class ContactRestController {
    private static Logger log = LoggerFactory.getLogger(ContactRestController.class);

    @Autowired
    private ContactService contactService;

    /*
        This method adds a self rel link to all cta
     */
    private static Resource<Contact> createContactResource(Contact contact) {
        Link contactLink = linkTo(methodOn(ContactRestController.class)
                .retrieveContact(contact.getId()))
                .withSelfRel();

        return new Resource<>(contact, contactLink);
    }

    @GetMapping(value = {"/contacts"})
    public Resources<Resource<Contact>> retrieveAllContacts() {
        log.debug(" retrieving all Contacts");
        List<Contact> contactList = contactService.findAll();
        return getResourcesOfContactListWithRelLinks(contactList);
    }

    @GetMapping("/contacts/{id}")
    public Resource<Contact> retrieveContact(@PathVariable Long id) {

        log.debug("Retrieving contact by Id");
        Contact contact = contactService.findById(id);
        return getContactResourceWithAllRel(contact);
    }

    @GetMapping("/contacts/{firstName}/{lastName}/{zipCode}")
    public Resources<Resource<Contact>> retrieveContactByFirstNameLastNameAndZipCode(
            @PathVariable String firstName, @PathVariable String lastName, @PathVariable String zipCode) {

        log.debug("finding contact by first name:{} last name:{} and zipcode: {}", firstName, lastName, zipCode);
        List<Contact> contactList = contactService.findByFirstNameLastNameAndZipCode(firstName, lastName, zipCode);
        return getResourcesOfContactListWithRelLinks(contactList);
    }

    @GetMapping("/contacts/{firstName}/{zipCode}")
    public Resources<Resource<Contact>> retrieveContactByFirstNameAndZipCode(
            @PathVariable String firstName, @PathVariable String zipCode) {

        log.debug("finding contact by first name:{}  and zipcode: {}", firstName, zipCode);
        List<Contact> contactList = contactService.findByFirstNameAndZip(firstName, zipCode);
        return getResourcesOfContactListWithRelLinks(contactList);
    }

    @PostMapping("/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<Contact> createContact(@Valid @RequestBody Contact contact) {
        log.debug(" Creating contact");
        return getContactResourceWithAllRel(
                contactService.save(contact));
    }

    @PutMapping("/contacts")
    @ResponseStatus(HttpStatus.OK)
    public Resource<Contact> updateContact(@Valid @RequestBody Contact contact) {
        log.debug("updating contact: {}", contact.getId());
        return getContactResourceWithAllRel(
                contactService.save(contact));
    }

    //== private helper methods ==

    @DeleteMapping("/contacts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable Long id) {
        log.debug("Deleting contact id: {}", id);
        contactService.deleteById(id);
    }

    private Resources<Resource<Contact>> getResourcesOfContactListWithRelLinks(List<Contact> contactList) {
        List<Resource<Contact>> contactResources =
                contactList.stream()
                        .map(ContactRestController::createContactResource)
                        .collect(Collectors.toList());

        Link selfRel = linkTo(methodOn(ContactRestController.class)
                .retrieveAllContacts())
                .withSelfRel();

        return new Resources<>(contactResources, selfRel);
    }

    /*
      This method adds all contacts rel link to cta and calls createContactResource()
      method which adds a seld rel link to cta.
     */
    private Resource<Contact> getContactResourceWithAllRel(Contact contact) {
        Link linkToAll = linkTo(methodOn(this.getClass())
                .retrieveAllContacts())
                .withRel("all-contacts");

        Resource<Contact> resource = createContactResource(contact);

        resource.add(linkToAll);
        return resource;
    }
}
