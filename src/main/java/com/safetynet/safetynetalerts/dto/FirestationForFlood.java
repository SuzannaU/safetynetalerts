package com.safetynet.safetynetalerts.dto;

import java.util.Set;

public class FirestationForFlood {

    private int firestationId;
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
