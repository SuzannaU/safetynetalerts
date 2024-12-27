package com.safetynet.safetynetalerts.dto;

import java.util.List;

public class AddressForFlood {

    private String address;
    private List<PersonForFlood> residents;
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public List<PersonForFlood> getResidents() {
        return residents;
    }
    public void setResidents(List<PersonForFlood> residents) {
        this.residents = residents;
    }
}
