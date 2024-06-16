package dataBase.DAO;

import app.model.Doctor;
import app.model.User;
import app.service.UserService;
import dataBase.DAO.DoctorDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DoctorDAOImpl implements DoctorDAO{
    private Connection connection;
    private UserService userService;



    public DoctorDAOImpl(Connection connection, UserService userService) {
        this.connection = connection;
        this.userService = userService;
    }

    @Override
    public void createDoctor(Doctor doctor) {
        try{
            userService.createUser(doctor);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Doctors (doctorID, consultationCost) VALUES (?,?)");
            preparedStatement.setInt(1, doctor.getDoctorID());
            preparedStatement.setDouble(2, doctor.getConsultationCost());
            preparedStatement.executeUpdate();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteDoctor(int doctorID) {
        try{
            userService.deleteUser(doctorID);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Doctors WHERE doctorID = ?");
            preparedStatement.setInt(1, doctorID);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Doctor getDoctor(int doctorID) {
        try{
            User user = userService.getUser(doctorID);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT consultationCost FROM Doctors WHERE doctorID = ?");
            preparedStatement.setInt(1, doctorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                double consultationCost = resultSet.getDouble("consultationCost");
                return new Doctor(user.getUserID(), user.getName(), user.getLastName(), user.getEmail(), user.getDNI(), user.getBirthDate(), doctorID, consultationCost);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateDoctor(Doctor doctor, int doctorid) {
        try{
            userService.updateUser(doctor, doctorid);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Doctors SET consultationCost = ? WHERE doctorID = ?");
            preparedStatement.setDouble(1, doctor.getConsultationCost());
            preparedStatement.setInt(2, doctorid);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Doctor> getDoctors() {
        
    }
}
