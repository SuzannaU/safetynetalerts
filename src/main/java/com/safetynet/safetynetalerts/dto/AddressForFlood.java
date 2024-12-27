package com.safetynet.safetynetalerts.dto;

import java.util.List;

public class AddressForFlood {

    private String address;
    private List<PersonForFire> residents;
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public List<PersonForFire> getResidents() {
        return residents;
    }
    public void setResidents(List<PersonForFire> residents) {
        this.residents = residents;
    }


}
