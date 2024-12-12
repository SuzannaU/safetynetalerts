package com.safetynet.safetynetalerts.model;

import java.util.List;

public class Firestation {

    private int id;
    private List<String> addresses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }
}
