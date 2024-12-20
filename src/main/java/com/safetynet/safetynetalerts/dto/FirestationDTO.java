package com.safetynet.safetynetalerts.dto;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class FirestationDTO {
    private String address;
    private String firestationId;

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getFirestationId() {
        return firestationId;
    }

    @JsonProperty("station")
    public void setFirestationId(String station) {
        this.firestationId = station;
    }
    
}
