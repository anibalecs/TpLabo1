package dataBase.DAO;

import app.model.Patient;
import app.model.User;
import app.service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDAO{
    private Connection connection;
    private UserService userService;

    public PatientDAOImpl(Connection connection, UserService userService) {
        this.connection = connection;
        this.userService = userService;
    }

    @Override
    public void createPatient(Patient patient) {
        try{
            userService.createUser(patient);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Patients (UserID, phoneNumber, alternativeNumber, allergies) VALUES (?,?,?,?)");
            preparedStatement.setInt(1, patient.getUserID());
            preparedStatement.setString(2, patient.getPhoneNumber());
            preparedStatement.setString(3, patient.getAlternativeNumber());
            preparedStatement.setString(4, patient.getAllergies());
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deletePatient(int patientID) {
        try{
            userService.deleteUser(patientID);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Patients WHERE patientID = ?");
            preparedStatement.setInt(1, patientID);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Patient getPatient(int patientID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT userID, phoneNumber, alternativeNumber, allergies FROM Patients WHERE patientID = ?");
            preparedStatement.setInt(1, patientID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userID = resultSet.getInt("userID");
                String phoneNumber = resultSet.getString("phoneNumber");
                String alternativeNumber = resultSet.getString("alternativeNumber");
                String allergies = resultSet.getString("allergies");
                User user = userService.getUser(userID);
                if (user != null) {
                    return new Patient(user.getUserID(), user.getName(), user.getLastName(), user.getEmail(), user.getDNI(), user.getBirthDate(), patientID, phoneNumber, alternativeNumber, allergies);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updatePatient(Patient patient, int patientID){
        try{
            userService.updateUser(patient, patient.getUserID());
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Patients SET phoneNumber = ?, alternativeNumber = ?, allergies = ? WHERE patientID = ?");
            preparedStatement.setString(1, patient.getPhoneNumber());
            preparedStatement.setString(2, patient.getAlternativeNumber());
            preparedStatement.setString(3, patient.getAllergies());
            preparedStatement.setInt(4, patientID);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Patient> getPatients() {
        List<Patient> patients = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Patients");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int patientID = resultSet.getInt("patientID");
                String phoneNumber = resultSet.getString("phoneNumber");
                String alternativeNumber = resultSet.getString("alternativeNumber");
                String allergies = resultSet.getString("allergies");
                int userID = resultSet.getInt("userID");
                User user = userService.getUser(userID);
                if (user != null) {
                    patients.add(new Patient(user.getUserID(), user.getName(), user.getLastName(), user.getEmail(), user.getDNI(), user.getBirthDate(), patientID, phoneNumber, alternativeNumber, allergies));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patients;
    }
}

