package dataBase.DAO;

import app.model.Doctor;

public interface DoctorDAO {
    int createDoctor(Doctor doctor);
    int deleteDoctor(Doctor doctor);
}
