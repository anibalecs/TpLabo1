package graficInterface.panel;

import app.model.Patient;
import app.service.PatientService;
import app.service.UserService;
import dataBase.DAO.*;
import dataBase.DataBaseConfig;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientPanel extends JPanel{
    private PatientService patientService;
    private DefaultTableModel tableModel;

    public PatientPanel(){
        UserDAO userDAO = new UserDAOImpl(DataBaseConfig.connect());
        UserService userService = new UserService(userDAO);
        PatientDAO patientDAO = new PatientDAOImpl(DataBaseConfig.connect(), userService);
        this.patientService = new PatientService(patientDAO);

        setLayout(new BorderLayout());

        String[] columnNames = {"Patient id", "Name", "Birth date", "Phone number"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane,BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Primera columna
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Last name:"), gbc);
        gbc.gridx = 1;
        JTextField lastNameField = new JTextField(20);
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Birth date(yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        JTextField birthDateField = new JTextField(20);
        formPanel.add(birthDateField, gbc);

        // Segunda columna
        gbc.gridx = 2;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Phone number:"), gbc);
        gbc.gridx = 3;
        JTextField numberField = new JTextField(20);
        formPanel.add(numberField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Alternative number:"), gbc);
        gbc.gridx = 3;
        JTextField number2Field = new JTextField(20);
        formPanel.add(number2Field, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        formPanel.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 3;
        JTextField DNIField = new JTextField(20);
        formPanel.add(DNIField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Allergies:"), gbc);
        gbc.gridx = 3;
        JTextField allergiesField = new JTextField(20);
        formPanel.add(allergiesField, gbc);

        // Botones
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton addButton = new JButton("Add patient");
        formPanel.add(addButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton deleteButton = new JButton("Delete patient");
        formPanel.add(deleteButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton updateButton = new JButton("Update patient");
        formPanel.add(updateButton, gbc);

        add(formPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e ->{
            String name =  nameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            int DNI = Integer.parseInt(DNIField.getText());
            String phoneNumber = numberField.getText();
            String alternativeNumber = number2Field.getText();
            String allergies = allergiesField.getText();
            String birthDateText = birthDateField.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = null;
            try{
                java.util.Date utilDate = sdf.parse(birthDateText);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                birthDate = sqlDate;
            }catch(ParseException ex){
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Patient newPatient = new Patient(name, lastName, email, DNI, (java.sql.Date) birthDate, phoneNumber, alternativeNumber, allergies);
            try{
                patientService.createPatient(newPatient);
                updateTable();
                JOptionPane.showMessageDialog(this, "Patient created successfully", "Sucess", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Error creating patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e->{
            int selectedRow = table.getSelectedRow();
            if(selectedRow >= 0){
                int patientID = (int) tableModel.getValueAt(selectedRow, 0);
                try{
                    patientService.deletePatient(patientID);
                    updateTable();
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error deleting patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(this, "Please select a patient to delete", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateButton.addActionListener(e->{
            int selectedRow = table.getSelectedRow();
            if(selectedRow >= 0){
                int patientID = (int) tableModel.getValueAt(selectedRow, 0);
                try{
                    Patient patient = patientService.getPatient(patientID);
                    goToEditPatientPanel(patient);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error fetching patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(this, "Please select a patient to update", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        updateTable();
    }

    private void updateTable(){
        tableModel.setRowCount(0);
        try{
            for(Patient patient : patientService.getPatients()){
                Object[] row = {patient.getPatientID(), patient.getName() + " " + patient.getLastName(), patient.getBirthDate(), patient.getPhoneNumber()};
                tableModel.addRow(row);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void goToEditPatientPanel(Patient patient){
        EditPatientPanel editPatientPanel = new EditPatientPanel(patientService);
        editPatientPanel.setPatient(patient);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        editPatientPanel.addPropertyChangeListener("patientUpdated", evt->{
            updateTable(); //Actualizar la tabla depues de cerrar el panel de edicion
            parentFrame.getContentPane().removeAll();
            parentFrame.add(this);
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        parentFrame.getContentPane().removeAll();
        parentFrame.add(editPatientPanel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
