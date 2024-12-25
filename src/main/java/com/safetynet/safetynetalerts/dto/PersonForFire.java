package com.safetynet.safetynetalerts.dto;

public class PersonForFire {
    private String lastName;
    private String phone;
    private MedicalRecordForFire medicalRecords;
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public MedicalRecordForFire getMedicalRecords() {
        return medicalRecords;
    }
    public void setMedicalRecords(MedicalRecordForFire medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    
}
