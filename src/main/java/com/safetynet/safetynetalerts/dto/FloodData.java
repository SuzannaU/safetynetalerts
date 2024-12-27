package com.safetynet.safetynetalerts.dto;

import java.util.Set;

public class FloodData {
    
    private Set<FirestationForFlood> stationsForFlood;

    public Set<FirestationForFlood> getStationsForFlood() {
        return stationsForFlood;
    }

    public void setStationsForFlood(Set<FirestationForFlood> stationsForFlood) {
        this.stationsForFlood = stationsForFlood;
    }

}
