package dataBase.DAO;

import app.model.ReportData;
import app.model.Shift;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShiftDAOImpl implements ShiftDAO{
    private Connection connection;

    public ShiftDAOImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void createShift(Shift shift){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Shifts (doctorID, patientID, dateTime) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, shift.getDoctorID());
            preparedStatement.setInt(2, shift.getPatientID());
            preparedStatement.setTimestamp(3, shift.getDateTime());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                shift.setShiftID(generatedKeys.getInt(1));
            } else{
                throw new SQLException("No ID obtained.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteShift(int shiftID){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Shifts WHERE shiftID = ?");
            preparedStatement.setInt(1, shiftID);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Shift getShift(int id){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Shifts WHERE shiftsID = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int doctorID = resultSet.getInt("doctorID");
                int patientID = resultSet.getInt("patientID");
                Timestamp dateTime = resultSet.getTimestamp("dateTime");
                return new Shift(id, doctorID, patientID, dateTime);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateShift(Shift shift, int id){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Shifts SET dateTime = ? WHERE shiftID = ?");
            preparedStatement.setTimestamp(1, shift.getDateTime());
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Shift> getShifts(){
        List<Shift> shifts = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Shifts");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int shiftID = resultSet.getInt("shiftID");
                int doctorID = resultSet.getInt("doctorID");
                int patientID = resultSet.getInt("patientID");
                Timestamp dateTime = resultSet.getTimestamp("dateTime");
                shifts.add(new Shift(shiftID, doctorID, patientID, dateTime));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return shifts;
    }

    @Override
    public boolean isDoctorAvailable(int doctorID, Timestamp dateTime){
        try{
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateTime);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1; //mes empieza en 0
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            int second = cal.get(Calendar.SECOND);

            // Verificar si la hora es exacta
            if(minute != 0 || second != 0){
                return false;
            }

            //Verificar si la hora esta entre el rango de trabajo
            Timestamp startOfDay = Timestamp.valueOf(String.format("%04d-%02d-%02d 10:00:00", year, month, day));
            Timestamp endOfDay = Timestamp.valueOf(String.format("%04d-%02d-%02d 19:00:00", year, month, day));
            if(dateTime.before(startOfDay) || dateTime.after(endOfDay)){
                return false;
            }

            Timestamp startDateTime = Timestamp.valueOf(String.format("%04d-%02d-%02d %02d:00:00", year, month, day, hour));
            cal.add(Calendar.HOUR_OF_DAY, 1);
            Timestamp nextHourDateTime = new Timestamp(cal.getTimeInMillis());

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM Shifts WHERE doctorID = ? AND dateTime >= ? AND dateTime < ?");
            preparedStatement.setInt(1, doctorID);
            preparedStatement.setTimestamp(2, startDateTime);
            preparedStatement.setTimestamp(3, nextHourDateTime);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1) == 0;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public List<Shift> getShiftsByPatientID(int patientID){
        List<Shift> shifts = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Shifts WHERE patientID = ?");
            preparedStatement.setInt(1, patientID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int shiftID = resultSet.getInt("shiftID");
                int doctorID = resultSet.getInt("doctorID");
                Timestamp dateTime = resultSet.getTimestamp("dateTime");
                shifts.add(new Shift(shiftID, doctorID, patientID, dateTime));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return shifts;
    }

    @Override
    public List<ReportData> getReportDataForDoctor(int doctorID, Timestamp startDate, Timestamp endDate) throws Exception{
        List<ReportData> reportDataList = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT dateTime AS date, COUNT(*) AS numberOfQueries, SUM(d.consultationCost) AS amountCharged " +
                    "FROM Shifts s " +
                    "JOIN Doctors d ON s.doctorID = d.doctorID " +
                    "WHERE s.doctorID = ? AND s.dateTime BETWEEN ? AND ? " +
                    "GROUP BY dateTime");
            preparedStatement.setInt(1, doctorID);
            preparedStatement.setTimestamp(2, startDate);
            preparedStatement.setTimestamp(3, endDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Timestamp date = resultSet.getTimestamp("date");
                int numberOfQueries = resultSet.getInt("numberOfQueries");
                double amountCharged = resultSet.getDouble("amountCharged");
                reportDataList.add(new ReportData(date, amountCharged, numberOfQueries));
            }
        } catch (SQLException e){
            throw new Exception("Error generating report data", e);
        }
        return reportDataList;
    }
}
