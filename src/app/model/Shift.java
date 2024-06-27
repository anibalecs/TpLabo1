package app.model;

import java.sql.Timestamp;

public class Shift {
    private int shiftID;
    private int doctorID;
    private int patientID;
    private Timestamp dateTime;

    public Shift(int shiftID, int doctorID, int patientID, Timestamp dateTime){
        this.shiftID = shiftID;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.dateTime = dateTime;
    }

    public Shift(int doctorID, int patientID, Timestamp dateTime){
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.dateTime = dateTime;
    }

    public int getShiftID() {
        return shiftID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public int getPatientID() {
        return patientID;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
}
