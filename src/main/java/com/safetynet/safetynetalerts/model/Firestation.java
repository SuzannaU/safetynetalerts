package com.safetynet.safetynetalerts.model;

import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"firestationId", "addresses"})
public class Firestation {
    private static final Logger logger = LoggerFactory.getLogger(Firestation.class);

    private String station;
    private int firestationId;
    private String address;
    private Set<String> addresses;

    @JsonCreator
    public Firestation(
            @JsonProperty("address") String address,
            @JsonProperty("station") String station) {

        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("'address' is missing");
        }
        if (station == null || station.isEmpty()) {
            throw new IllegalArgumentException("'station' is missing");
        }
        this.address = address;
        this.station = station;
        logger.debug("Firestation created");
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public int getFirestationId() {
        return firestationId;
    }

    public void setFirestationId(int firestationId) {
        this.firestationId = firestationId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<String> addresses) {
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Firestation that = (Firestation) o;
        return firestationId == that.firestationId && Objects.equals(addresses, that.addresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firestationId, addresses);
    }
}
