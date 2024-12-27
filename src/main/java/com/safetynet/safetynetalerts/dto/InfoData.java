package com.safetynet.safetynetalerts.dto;

import java.util.List;

public class InfoData {
    private String lastName;
    private List<PersonForInfo> persons;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<PersonForInfo> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonForInfo> persons) {
        this.persons = persons;
    }

}
