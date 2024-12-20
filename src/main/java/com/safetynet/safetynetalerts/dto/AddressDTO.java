package com.safetynet.safetynetalerts.dto;

import java.util.List;

public class AddressDTO {

    String address;
    int firestationId;
    int numberOfAdults;
    int numberOfChildren;
    List<PersonDTO> residents; //to be filtered according to age if needed

}
