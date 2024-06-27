package app.model;

import java.sql.Date;

public class Patient extends User{
    private int patientID;
    private String phoneNumber;
    private String AlternativeNumber;
    private String allergies;

    public Patient(int userID, String name, String lastName, String email, int DNI, Date birthDate, int patientID, String phoneNumber, String alternativeNumber, String allergies) {
        super(userID, name, lastName, email, DNI, birthDate);
        this.patientID = patientID;
        this.phoneNumber = phoneNumber;
        AlternativeNumber = alternativeNumber;
        this.allergies = allergies;
    }

    public Patient(String name, String lastName, String email, int DNI, Date birthDate, String phoneNumber, String alternativeNumber, String allergies) {
        super(name, lastName, email, DNI, birthDate);
        this.phoneNumber = phoneNumber;
        AlternativeNumber = alternativeNumber;
        this.allergies = allergies;
    }

    public int getPatientID() {
        return patientID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAlternativeNumber() {
        return AlternativeNumber;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAlternativeNumber(String alternativeNumber) {
        AlternativeNumber = alternativeNumber;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }
}
