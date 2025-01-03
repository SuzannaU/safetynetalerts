package com.safetynet.safetynetalerts.dto;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FirestationForFlood {

    @JsonProperty(value="station")
    private int firestationId;
    
    @JsonProperty(value="addresses")
    Set<AddressForFlood> addressesForFlood;

    public int getFirestationId() {
        return firestationId;
    }

    public void setFirestationId(int firestationId) {
        this.firestationId = firestationId;
    }

    public Set<AddressForFlood> getAddressesForFlood() {
        return addressesForFlood;
    }

    public void setAddressesForFlood(Set<AddressForFlood> addressesForFlood) {
        this.addressesForFlood = addressesForFlood;
    }
}
