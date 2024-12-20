package com.safetynet.safetynetalerts.dto;

import java.util.List;
import com.safetynet.safetynetalerts.model.MedicalRecord;

public class PersonDTO {
    
    String firstName;
    String lastName;
    String address;
    String phone; 
    int age;
    int firestationId; 
    List<MedicalRecord> medicalRecords;     

}
