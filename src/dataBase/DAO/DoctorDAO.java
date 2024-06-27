    package dataBase.DAO;

    import app.model.Doctor;
    import java.util.List;

    public interface DoctorDAO {
        void createDoctor(Doctor doctor);
        void deleteDoctor(int id);
        Doctor getDoctor(int id);
        void updateDoctor(Doctor doctor, int id);
        List<Doctor> getDoctors();
    }
