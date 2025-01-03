package com.safetynet.safetynetalerts.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "station", "residents" })
public class FireData {

    @JsonProperty(value="station")
    private int firestationId;

    private List<PersonForFire> residents;

    public List<PersonForFire> getResidents() {
        return residents;
    }

    public void setResidents(List<PersonForFire> residents) {
        this.residents = residents;
    }

    public int getFirestationId() {
        return firestationId;
    }

    public void setFirestationId(int firestationId) {
        this.firestationId = firestationId;
    }

}
