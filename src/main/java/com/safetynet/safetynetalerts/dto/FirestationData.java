package com.safetynet.safetynetalerts.dto;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "count", "persons" })
public class FirestationData {

    List<PersonForStation> persons;
    Map<String, Long> count;

    public List<PersonForStation> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonForStation> persons) {
        this.persons = persons;
    }

    public Map<String, Long> getCount() {
        return count;
    }

    public void setCount(Map<String, Long> count) {
        this.count = count;
    }

}
