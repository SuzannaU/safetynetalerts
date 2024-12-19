package com.safetynet.safetynetalerts.model;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecord {
    private String personId;
    private LocalDate birthdate;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }


}
