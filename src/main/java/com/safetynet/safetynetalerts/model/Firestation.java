package com.safetynet.safetynetalerts.model;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Firestation {

    private int firestationId;
    private List<String> addresses;

    public int getFirestationId() {
        return firestationId;
    }

    public void setFirestationId(int firestationId) {
        this.firestationId = firestationId;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }
}
