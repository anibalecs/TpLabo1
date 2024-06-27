package dataBase.DAO;

import app.model.ReportData;
import app.model.Shift;
import java.sql.Timestamp;
import java.util.List;

public interface ShiftDAO{
    void createShift(Shift shift);
    void deleteShift(int id);
    Shift getShift(int id);
    void updateShift(Shift shift, int id);
    List<Shift> getShifts();
    boolean isDoctorAvailable(int doctorID, Timestamp dateTime);
    List<Shift> getShiftsByPatientID(int patientID);
    List<ReportData> getReportDataForDoctor(int doctorID, Timestamp startDate, Timestamp endDate) throws Exception;
}
