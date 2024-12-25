package com.safetynet.safetynetalerts.dto;

import java.util.List;

public class ChildAlertData {

    List<ChildForChildAlert> children;
    List<AdultForChildAlert> adults;

    public List<ChildForChildAlert> getChildren() {
        return children;
    }

    public void setChildren(List<ChildForChildAlert> children) {
        this.children = children;
    }

    public List<AdultForChildAlert> getAdults() {
        return adults;
    }

    public void setAdults(List<AdultForChildAlert> adults) {
        this.adults = adults;
    }

}
