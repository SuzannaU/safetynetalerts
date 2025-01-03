package com.safetynet.safetynetalerts.dto;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonProperty;


public class FloodData {
    @JsonProperty(value="stations")
    private Set<FirestationForFlood> stationsForFlood;

    public Set<FirestationForFlood> getStationsForFlood() {
        return stationsForFlood;
    }

    public void setStationsForFlood(Set<FirestationForFlood> stationsForFlood) {
        this.stationsForFlood = stationsForFlood;
    }

}
