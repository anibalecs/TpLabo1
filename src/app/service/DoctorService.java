package app.service;

import app.model.Doctor;
import dataBase.DAO.DoctorDAO;
import java.util.List;

public class DoctorService {
    private DoctorDAO doctorDAO;

    public DoctorService(DoctorDAO doctorDAO){
        this.doctorDAO = doctorDAO;
    }

    public void createDoctor(Doctor doctor) throws Exception {
        try{
            doctorDAO.createDoctor(doctor);
        }catch(Exception e){
            throw new Exception("Error creating Doctor");
        }
    }

    public void deleteDoctor(int id) throws Exception {
        try{
            doctorDAO.deleteDoctor(id);
        }catch(Exception e){
            throw new Exception("Error deleting Doctor");
        }
    }

    public Doctor getDoctor(int id) throws Exception {
        try{
            return doctorDAO.getDoctor(id);
        }catch(Exception e){
            throw new Exception("Error getting Doctor");
        }
    }

    public void updateDoctor(Doctor doctor, int id) throws Exception {
        try{
            doctorDAO.updateDoctor(doctor, id);
        }catch(Exception e){
            throw new Exception("Error updating Doctor");
        }
    }

    public List<Doctor> getDoctors() throws Exception {
        try{
            return doctorDAO.getDoctors();
        }catch(Exception e){
            throw new Exception("Error getting Doctors");
        }
    }
}
