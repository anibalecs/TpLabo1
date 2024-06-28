package dataBase.DAO;

import app.model.Patient;
import java.util.List;

public interface PatientDAO{
    void createPatient(Patient patient);
    void deletePatient(int id);
    Patient getPatient(int id);
    void updatePatient(Patient patient, int id);
    List<Patient> getPatients();
}
