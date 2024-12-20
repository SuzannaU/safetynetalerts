package com.safetynet.safetynetalerts.model;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Person {

    private String firstName;
    private String lastName;
    private String personId;
    private LocalDate birthdate;
    private int age;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    private List<String> medications;
    private List<String> allergies;
    private int firestationId;

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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
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


    public String getAddress() {
        return address;
    }


    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }


    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
