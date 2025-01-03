package com.safetynet.safetynetalerts.dto;

import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "stations", "residents" })
public class FireData {

    @JsonProperty(value="stations")
    private Set<Integer> firestationIds;

    private List<PersonForFire> residents;

    public List<PersonForFire> getResidents() {
        return residents;
    }

    public void setResidents(List<PersonForFire> residents) {
        this.residents = residents;
    }

    public Set<Integer> getFirestationIds() {
        return firestationIds;
    }

    public void setFirestationIds(Set<Integer> firestationIds) {
        this.firestationIds = firestationIds;
    }

}
