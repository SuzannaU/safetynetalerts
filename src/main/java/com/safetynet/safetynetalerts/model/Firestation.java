package com.safetynet.safetynetalerts.model;

import java.util.Set;

public class Firestation {

    private int firestationId;
    private Set<String> addresses;

    public int getFirestationId() {
        return firestationId;
    }

    public void setFirestationId(int firestationId) {
        this.firestationId = firestationId;
    }

    public Set<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<String> addresses) {
        this.addresses = addresses;
    }
}
