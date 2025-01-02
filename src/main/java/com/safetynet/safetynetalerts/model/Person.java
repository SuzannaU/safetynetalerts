package com.safetynet.safetynetalerts.model;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"personId", "birthdate", "age", "category", "medications", "allergies",
        "firestationId"})
public class Person {
    private static final Logger logger = LoggerFactory.getLogger(Person.class);

    private String firstName;
    private String lastName;
    private String personId;
    private LocalDate birthdate;
    private int age;
    private String category;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    private List<String> medications;
    private List<String> allergies;
    private int firestationId;

    @JsonCreator
    public Person(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("address") String address,
            @JsonProperty("city") String city,
            @JsonProperty("zip") String zip,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email) {

        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("'firstName' is missing");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("'lastName' is missing");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.personId = firstName.concat(lastName);
        logger.debug("Person created");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPersonId() {
        return personId;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public int getFirestationId() {
        return firestationId;
    }

    public void setFirestationId(int firestationId) {
        this.firestationId = firestationId;
    }
}
