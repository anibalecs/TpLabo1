package graficInterface.panel;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import app.model.Doctor;
import app.service.DoctorService;
import app.service.UserService;
import dataBase.DAO.DoctorDAO;
import dataBase.DAO.DoctorDAOImpl;
import dataBase.DAO.UserDAO;
import dataBase.DAO.UserDAOImpl;
import dataBase.DataBaseConfig;

public class DoctorPanel extends JPanel{
    private DoctorService doctorService;
    private DefaultTableModel tableModel;
    private JTextField nameField, lastNameField, emailField, costField, DNIField, birthDateField;
    private JButton addButton, updateButton;
    private Doctor currentDoctor;

    public DoctorPanel() throws Exception{
        UserDAO userDAO = new UserDAOImpl(DataBaseConfig.connect());
        UserService userService = new UserService(userDAO);
        DoctorDAO doctorDAO = new DoctorDAOImpl(DataBaseConfig.connect(), userService);
        this.doctorService = new DoctorService(doctorDAO);

        setLayout(new BorderLayout());

        String[] columnNames = {"Doctor id", "Name", "Consultation cost"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); //espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Primera columna
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Last name:"), gbc);
        gbc.gridx = 1;
        lastNameField = new JTextField(20);
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        //Segunda columna
        gbc.gridx = 2;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Consultation cost:"), gbc);
        gbc.gridx = 3;
        costField = new JTextField(20);
        formPanel.add(costField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        formPanel.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 3;
        DNIField = new JTextField(20);
        formPanel.add(DNIField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Birth date(yyyy-MM-dd):"), gbc);
        gbc.gridx = 3;
        birthDateField = new JTextField(20);
        formPanel.add(birthDateField, gbc);

        //botones
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        addButton = new JButton("Add doctor");
        formPanel.add(addButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton deleteButton = new JButton("Delete doctor");
        formPanel.add(deleteButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        updateButton = new JButton("Update doctor");
        formPanel.add(updateButton, gbc);

        add(formPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e->{
            String name = nameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String dniText = DNIField.getText();

            if(dniText.length() > 9){
                JOptionPane.showMessageDialog(this, "DNI must not exceed 9 characters", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int DNI = Integer.parseInt(DNIField.getText());
            double consultationCost = Double.parseDouble(costField.getText());
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

            Doctor newDoctor = new Doctor(name, lastName, email, DNI, (java.sql.Date) birthDate, consultationCost);
            try{
                doctorService.createDoctor(newDoctor);
                updateTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Doctor created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Error creating doctor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e->{
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int doctorID = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    doctorService.deleteDoctor(doctorID);
                    updateTable();
                    JOptionPane.showMessageDialog(this, "Doctor deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting doctor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a doctor to delete", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateButton.addActionListener(e->{
            int selectedRow = table.getSelectedRow();
            if(selectedRow >= 0){
                int doctorID = (int) tableModel.getValueAt(selectedRow, 0);
                try{
                    Doctor doctor = doctorService.getDoctor(doctorID);
                    fillFieldEdit(doctor);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error fetching doctor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(this, "Please select a doctor to update", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        updateTable();
    }

    private void updateTable() throws Exception{
        tableModel.setRowCount(0);
        try{
            for(Doctor doctor : doctorService.getDoctors()){
                Object[] row = {doctor.getDoctorID(), doctor.getName() + " " + doctor.getLastName(), doctor.getConsultationCost()};
                tableModel.addRow(row);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void fillFieldEdit(Doctor doctor){
        this.currentDoctor = doctor;
        JTextField nameField = new JTextField(doctor.getName(), 20);
        JTextField lastNameField = new JTextField(doctor.getLastName(), 20);
        JTextField emailField = new JTextField(doctor.getEmail(), 20);
        JTextField costField = new JTextField(String.valueOf(doctor.getConsultationCost()), 20);
        JTextField DNIField = new JTextField(String.valueOf(doctor.getDNI()), 20);
        JTextField birthDateField = new JTextField(doctor.getBirthDate().toString(), 20);

        int result = JOptionPane.showConfirmDialog(this, new Object[]{
                "Name:", nameField,
                "Last name:", lastNameField,
                "Email:", emailField,
                "DNI:", DNIField,
                "Consultation cost:", costField,
                "Birth date (yyyy-MM-dd):", birthDateField,
        }, "Edit doctor", JOptionPane.OK_CANCEL_OPTION);

        if(result == JOptionPane.OK_OPTION){
            try{
               String name = nameField.getText();
               String lastName = lastNameField.getText();
               String email = emailField.getText();
               int DNI = Integer.parseInt(DNIField.getText());
               Double consultationCost = Double.valueOf(costField.getText());
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
               currentDoctor.setName(name);
               currentDoctor.setLastName(lastName);
               currentDoctor.setEmail(email);
               currentDoctor.setDNI(DNI);
               currentDoctor.setConsultationCost(consultationCost);
               currentDoctor.setBirthDate((java.sql.Date) birthDate);
               doctorService.updateDoctor(currentDoctor, currentDoctor.getDoctorID());
               updateTable();
               clearForm();
               JOptionPane.showMessageDialog(this, "Doctor updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Error updating doctor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm(){
        //limpiar all
        for(Component c : getComponents()){
            if(c instanceof JTextField){
                ((JTextField) c).setText("");
            }
        }
    }
}
