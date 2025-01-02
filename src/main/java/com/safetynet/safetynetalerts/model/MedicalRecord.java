package com.safetynet.safetynetalerts.model;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"personId", "localBirthdate", "age"})
public class MedicalRecord {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecord.class);

    private String firstName;
    private String lastName;
    private String personId;
    private String rawBirthdate;
    private LocalDate localBirthdate;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    @JsonCreator
    public MedicalRecord(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("birthdate") String rawBirthdate,
            @JsonProperty("medications") List<String> medications,
            @JsonProperty("allergies") List<String> allergies) {

        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("'firstName' is missing");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("'lastName' is missing");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.personId = firstName.concat(lastName);
        this.rawBirthdate = rawBirthdate;
        this.medications = medications;
        this.allergies = allergies;
        logger.debug("MedicalRecord created");
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

    public String getRawBirthdate() {
        return rawBirthdate;
    }

    public void setRawBirthdate(String rawBirthdate) {
        this.rawBirthdate = rawBirthdate;
    }

    public LocalDate getLocalBirthdate() {
        return localBirthdate;
    }

    public void setLocalBirthdate(LocalDate localBirthdate) {
        this.localBirthdate = localBirthdate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
