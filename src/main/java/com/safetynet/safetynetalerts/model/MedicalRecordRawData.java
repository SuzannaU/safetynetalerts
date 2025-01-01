package com.safetynet.safetynetalerts.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MedicalRecordRawData {
    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

    @JsonCreator
    public MedicalRecordRawData(@JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName) {

        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("'firstName' is missing");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("'lastName' is missing");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
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



}
