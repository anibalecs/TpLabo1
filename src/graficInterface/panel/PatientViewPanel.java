package graficInterface.panel;

import app.model.Doctor;
import app.model.Shift;
import app.service.DoctorService;
import app.service.ShiftService;
import app.service.UserService;
import dataBase.DAO.*;
import dataBase.DataBaseConfig;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientViewPanel extends JPanel{
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField patientIDField;
    private ShiftService shiftService;
    private DoctorService doctorService;

    public PatientViewPanel(){
        ShiftDAO shiftDAO = new ShiftDAOImpl(DataBaseConfig.connect());
        UserDAO userDAO = new UserDAOImpl(DataBaseConfig.connect());
        UserService userService = new UserService(userDAO);
        DoctorDAO doctorDAO = new DoctorDAOImpl(DataBaseConfig.connect(), userService);
        this.shiftService = new ShiftService(shiftDAO);
        this.doctorService = new DoctorService(doctorDAO);

        setLayout(new BorderLayout());

        String[] columnNames = {"Date", "Hour", "Doctor"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        //seleccionar paciente
        JPanel formPanel = new JPanel(new GridLayout(3, 1));
        formPanel.add(new JLabel("Patient id:"));
        patientIDField = new JTextField();
        formPanel.add(patientIDField);

        JButton viewButton = new JButton("See shifts");
        formPanel.add(viewButton);

        add(formPanel, BorderLayout.NORTH);

        viewButton.addActionListener(e -> {
            int patientId = Integer.parseInt(patientIDField.getText());
            try {
                updateTableWithPatientShifts(patientId);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void updateTableWithPatientShifts(int patientId) throws Exception{
        List<Shift> shifts = shiftService.getShiftsByPatientID(patientId);
        tableModel.setRowCount(0); //para limpiar la tabla antes de actualizar
        for(Shift shift : shifts){
            Doctor doctor = doctorService.getDoctor(shift.getDoctorID());
            Object[] row = {
                    shift.getDateTime().toLocalDateTime().toLocalDate(),
                    shift.getDateTime().toLocalDateTime().toLocalTime(),
                    doctor != null ? doctor.getName() + " " + doctor.getLastName() : "Unknow"};
            tableModel.addRow(row);
        }
    }
}
