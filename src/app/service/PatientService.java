package app.service;

import app.model.Doctor;
import app.model.Patient;
import dataBase.DAO.PatientDAO;

import java.util.List;

public class PatientService{
    private PatientDAO patientDAO;

    public PatientService(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public void createPatient(Patient patient) throws Exception {
        try{
            patientDAO.createPatient(patient);
        }catch(Exception e){
            throw new Exception("Error creating Patient");
        }
    }

    public void deletePatient(int id) throws Exception{
        try{
            patientDAO.deletePatient(id);
        }catch(Exception e){
            throw new Exception("Error deleting Patient");
        }
    }

    public Patient getPatient(int id) throws Exception {
        try{
            return patientDAO.getPatient(id);
        }catch(Exception e){
            throw new Exception("Error getting Patient");
        }
    }

    public void updatePatient(Patient patient, int id) throws Exception {
        try{
            patientDAO.updatePatient(patient, id);
        }catch(Exception e){
            throw new Exception("Error updating Patient");
        }
    }

    public List<Patient> getPatients() throws Exception {
        try{
            return patientDAO.getPatients();
        }catch(Exception e){
            throw new Exception("Error getting Patients");
        }
    }
}
