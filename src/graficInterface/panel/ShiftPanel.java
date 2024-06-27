package graficInterface.panel;

import app.model.Doctor;
import app.model.Patient;
import app.model.Shift;
import app.service.DoctorService;
import app.service.PatientService;
import app.service.ShiftService;
import app.service.UserService;
import dataBase.DAO.*;
import dataBase.DataBaseConfig;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ShiftPanel extends JPanel {
    private ShiftService shiftService;
    private DoctorService doctorService;
    private PatientService patientService;
    private DefaultTableModel tableModel;

    public ShiftPanel() {
        ShiftDAO shiftDAO = new ShiftDAOImpl(DataBaseConfig.connect());
        UserDAO userDAO = new UserDAOImpl(DataBaseConfig.connect());
        UserService userService = new UserService(userDAO);
        DoctorDAO doctorDAO = new DoctorDAOImpl(DataBaseConfig.connect(), userService);
        PatientDAO patientDAO = new PatientDAOImpl(DataBaseConfig.connect(), userService);

        this.patientService = new PatientService(patientDAO);
        this.doctorService = new DoctorService(doctorDAO);
        this.shiftService = new ShiftService(shiftDAO);

        setLayout(new BorderLayout());

        // Tabla de turnos
        String[] columnNames = {"ID", "Doctor", "Paciente", "Fecha y hora"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Formulario para agregar turnos
        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Doctor:"));
        JComboBox<Doctor> doctorComboBox = new JComboBox<>();
        formPanel.add(doctorComboBox);
        formPanel.add(new JLabel("Paciente:"));
        JComboBox<Patient> patientComboBox = new JComboBox<>();
        formPanel.add(patientComboBox);
        formPanel.add(new JLabel("Fecha y hora(yyyy-MM-dd HH:mm):"));
        JTextField dateTimeField = new JTextField();
        formPanel.add(dateTimeField);
        JButton addButton = new JButton("Agregar turno");
        formPanel.add(addButton);
        JButton deleteButton = new JButton("Eliminar turno");
        formPanel.add(deleteButton);

        add(formPanel, BorderLayout.SOUTH);

        loadDoctors(doctorComboBox);
        loadPatients(patientComboBox);

        addButton.addActionListener(e -> {
            try {
                Doctor selectedDoctor = (Doctor) doctorComboBox.getSelectedItem();
                Patient selectedPatient = (Patient) patientComboBox.getSelectedItem();
                String dateTimeString = dateTimeField.getText();
                if (selectedDoctor == null || selectedPatient == null || dateTimeString.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Timestamp dateTime = new Timestamp(dateTimeFormat.parse(dateTimeString).getTime());

                if (!shiftService.isDoctorAvailable(selectedDoctor.getDoctorID(), dateTime)) {
                    JOptionPane.showMessageDialog(this, "El doctor no está disponible en esa fecha y hora.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Shift newShift = new Shift(selectedDoctor.getDoctorID(), selectedPatient.getPatientID(), dateTime);
                shiftService.createShift(newShift);
                updateTable();
                JOptionPane.showMessageDialog(this, "Turno creado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha u hora inválido. Por favor use yyyy-MM-dd para la fecha y HH:mm para la hora.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear el turno: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un turno para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int shiftID = (int) table.getValueAt(selectedRow, 0);
            try {
                shiftService.deleteShift(shiftID);
                updateTable();
                JOptionPane.showMessageDialog(this, "Turno eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el turno: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateTable();
    }

    private void loadDoctors(JComboBox<Doctor> doctorComboBox) {
        try {
            List<Doctor> doctors = doctorService.getDoctors();
            for (Doctor doctor : doctors) {
                doctorComboBox.addItem(doctor);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar doctores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Renderer to display the full name
        doctorComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Doctor) {
                    Doctor doctor = (Doctor) value;
                    setText(doctor.getName() + " " + doctor.getLastName());
                }
                return this;
            }
        });
    }

    private void loadPatients(JComboBox<Patient> patientComboBox) {
        try {
            List<Patient> patients = patientService.getPatients();
            for (Patient patient : patients) {
                patientComboBox.addItem(patient);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar pacientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Renderer to display the full name
        patientComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Patient) {
                    Patient patient = (Patient) value;
                    setText(patient.getName() + " " + patient.getLastName());
                }
                return this;
            }
        });
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        try {
            List<Shift> shifts = shiftService.getShifts();
            for (Shift shift : shifts) {
                Doctor doctor = doctorService.getDoctor(shift.getDoctorID());
                Patient patient = patientService.getPatient(shift.getPatientID());
                Object[] row = {shift.getShiftID(), doctor.getName() + " " + doctor.getLastName(), patient.getName() + " " + patient.getLastName(), shift.getDateTime()};
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
