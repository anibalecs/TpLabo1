package dataBase.DAO;

import app.model.Doctor;
import app.model.User;
import app.service.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAOImpl implements DoctorDAO{
    private Connection connection;
    private UserService userService;

    public DoctorDAOImpl(Connection connection, UserService userService) {
        this.connection = connection;
        this.userService = userService;
    }

    @Override
    public void createDoctor(Doctor doctor){
        try{
            userService.createUser(doctor);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Doctors (userID, consultationCost) VALUES (?,?)");
            preparedStatement.setInt(1, doctor.getUserID());
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
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT userID, consultationCost FROM Doctors WHERE doctorID = ?");
            preparedStatement.setInt(1, doctorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userID = resultSet.getInt("userID");
                double consultationCost = resultSet.getDouble("consultationCost");
                User user = userService.getUser(userID);
                if (user != null) {
                    return new Doctor(user.getUserID(), user.getName(), user.getLastName(), user.getEmail(), user.getDNI(), user.getBirthDate(), doctorID, consultationCost);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateDoctor(Doctor doctor, int doctorid) {
        try{
            userService.updateUser(doctor, doctor.getUserID());
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Doctors SET consultationCost = ? WHERE doctorID = ?");
            preparedStatement.setDouble(1, doctor.getConsultationCost());
            preparedStatement.setInt(2, doctorid);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Doctor> getDoctors(){
        List<Doctor> doctors = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Doctors");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int doctorID = resultSet.getInt("doctorID");
                double consultationCost = resultSet.getDouble("consultationCost");
                int userID = resultSet.getInt("userID");
                User user = userService.getUser(userID);
                if(user != null){
                    doctors.add(new Doctor(user.getUserID(), user.getName(), user.getLastName(), user.getEmail(), user.getDNI(), user.getBirthDate(), doctorID, consultationCost));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return doctors;
    }
}
