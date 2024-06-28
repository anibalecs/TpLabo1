package app.model;

import java.sql.Date;

public class Doctor extends User{
    private int doctorID;
    private double consultationCost;

    public Doctor(int userID, String name, String lastName, String email, int DNI, Date birthDate, int doctorID, double consultationCost){
        super(userID, name, lastName, email, DNI, birthDate);
        this.doctorID = doctorID;
        this.consultationCost = consultationCost;
    }

    public Doctor(String name, String lastName, String email, int DNI, Date birthDate, double consultationCost){
        super(name, lastName, email, DNI, birthDate);
        this.consultationCost = consultationCost;
    }

    public int getDoctorID(){
        return doctorID;
    }

    public double getConsultationCost(){
        return consultationCost;
    }

    public void setDoctorID(int doctorID){
        this.doctorID = doctorID;
    }

    public void setConsultationCost(double consultationCost){
        this.consultationCost = consultationCost;
    }
}
