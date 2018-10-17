package com.xyz.cta.rest.ws.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xyz.cta.rest.ws.validator.ValidZipCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@ApiModel(description = "Contact information so that promotional materials and free product samples" +
        " of XYZ Diet Water can be sent to the consumers and investors.")
@ValidZipCode
@Entity
public class Contact {
    //== Private Fields
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty(notes = "${contact.api.full.name}")
    @NotBlank(message = "${contact.full.name.not.blank}")
    @Pattern(regexp = "^[a-zA-Z-]{2,20}\\s[a-zA-Z-]{2,20}$", message = "${contact.full.name.error}")
    @Transient
    private String fullName;

    @JsonIgnore
    private String firstName;
    @JsonIgnore
    private String lastName;

    @ApiModelProperty(notes = "${contact.api.emai}")
    @Email(message = "${contact.email.error}")
    private String email;

    @ApiModelProperty(notes = "${contact.api.address.line.1}")
    @NotBlank(message = "${contact.address.line.1}")
    private String addressLine1;

    @ApiModelProperty(notes = "${contact.api.address.line.2}")
    private String addressLine2;

    @ApiModelProperty(notes = "${contact.api.city}")
    @NotBlank(message = "${contact.city.error}")
    private String city;

    @ApiModelProperty(notes = "${contact.api.state}")
    @NotBlank(message = "${contact.state.error}")
    @Pattern(regexp = "^[a-zA-Z]{2}$", message = "${contact.state.error}")
    private String state;

    @ApiModelProperty(notes = "zipcode can be only 5 digits ")
    @Pattern(regexp = "^\\d{5}$", message = "Zip Code should have 5 digits")
    private String zipCode;

    @ApiModelProperty(notes = "${contact.api.country}")
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]{3}$", message = "${contact.country.error}")
    private String country;

    @ApiModelProperty(notes = "${contact.api.phone}")
    @Pattern(regexp = "^[2-9]\\d{2}-\\d{3}-\\d{4}$", message = "${contact.phone.error}")
    private String phone;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;


    //== Class Constructors

    public Contact() {
    }

    public Contact(String fullName,
                   String email,
                   String addressLine1,
                   String addressLine2,
                   String city,
                   String state,
                   String zipCode,
                   String country,
                   String phone) {

        //this.fullName = fullName;
        this.email = email;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.phone = phone;
        setFullName(fullName);
    }


    //==Getter and Setter methods

    public Long getId() {
        return id;
    }


    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
        if (!StringUtils.isEmpty(fullName)) {
            String[] name = fullName.split(" ");
            firstName = name[0];
            lastName = name[1];
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @PostConstruct
    public void init() {
        System.out.println("\n============================post constuct" + fullName);
    }
}

