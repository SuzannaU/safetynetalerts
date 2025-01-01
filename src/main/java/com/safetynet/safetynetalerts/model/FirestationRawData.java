package com.safetynet.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FirestationRawData {
    private String address;
    private String firestationId;

    @JsonCreator
    public FirestationRawData(
            @JsonProperty("address") String address,
            @JsonProperty("station") String firestationId) {
                
                if (address == null || address.isEmpty()) {
                    throw new IllegalArgumentException("'address' is missing");
                }
                if (firestationId == null || firestationId.isEmpty()) {
                    throw new IllegalArgumentException("'firestationId' is missing");
                }
        this.address = address;
        this.firestationId = firestationId;
    }

    public String getAddress() {
        return address;
    }

    @JsonProperty("station")
    public String getFirestationId() {
        return firestationId;
    }
}
