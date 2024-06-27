package app.service;

import app.model.ReportData;
import app.model.Shift;
import dataBase.DAO.ShiftDAO;
import java.sql.Timestamp;
import java.util.List;

public class ShiftService{
    private ShiftDAO shiftDAO;

    public ShiftService(ShiftDAO shiftDAO) {
        this.shiftDAO = shiftDAO;
    }

    public void createShift(Shift shift) throws Exception {
        try {
            if(shiftDAO.isDoctorAvailable(shift.getDoctorID(), shift.getDateTime())){
                shiftDAO.createShift(shift);
            }else{
                throw new Exception("The doctor is not available at the selected time.");
            }
        }catch(Exception e){
            throw new Exception("Error creating shift");
        }
    }

    public void deleteShift(int id) throws Exception {
        try{
            shiftDAO.deleteShift(id);
        }catch(Exception e){
            throw new Exception("Error deleting Shift");
        }
    }

    public Shift getShift(int id) throws Exception {
        try{
            return shiftDAO.getShift(id);
        }catch(Exception e){
            throw new Exception("Error getting Doctor");
        }
    }

    public void updateShift(Shift shift, int id) throws Exception {
        try{
            shiftDAO.updateShift(shift, id);
        }catch(Exception e){
            throw new Exception("Error updating Shift");
        }
    }

    public List<Shift> getShifts() throws Exception {
        try{
            return shiftDAO.getShifts();
        }catch(Exception e){
            throw new Exception("Error getting Doctors");
        }
    }

    public boolean isDoctorAvailable(int doctorID, Timestamp dateTime) throws Exception {
        try {
            return shiftDAO.isDoctorAvailable(doctorID, dateTime);
        }catch(Exception e){
            throw new Exception("Error in availability check");
        }
    }

    public List<Shift> getShiftsByPatientID(int patientID) throws Exception {
        try {
            return shiftDAO.getShiftsByPatientID(patientID);
        } catch (Exception e) {
            throw new Exception("Error in search of shifts");
        }
    }

    public List<ReportData> getReportDataForDoctor(int doctorID, Timestamp startDate, Timestamp endDate) throws Exception {
        try {
            return shiftDAO.getReportDataForDoctor(doctorID, startDate, endDate);
        } catch (Exception e) {
            throw new Exception("Error generating report data", e);
        }
    }
}
