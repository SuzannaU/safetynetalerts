package com.safetynet.safetynetalerts.service;

import java.time.LocalDate;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    public String setId(String firstName, String lastName) {
        String personId = firstName.concat(lastName);
        return personId;
    }

    /*
    public LocalDate setbirthdate(String birthdate){
        String unformattedBirthdate = birthdate; 
        LocalDate formattedBirthdate; 
        //use Parse() API to convert String MM/dd/yyyy to BASIC_ISO_DATE? return formattedBirthdate; 
    }
    */

    public void setAge(LocalDate birthDate) {
        Date today = new Date();
        // this.age = today - birthDate; figure out how. Calendar class?
    }
}
