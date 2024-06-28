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

    public DoctorPanel(){
        UserDAO userDAO = new UserDAOImpl(DataBaseConfig.connect());
        UserService userService = new UserService(userDAO);
        DoctorDAO doctorDAO = new DoctorDAOImpl(DataBaseConfig.connect(), userService);
        this.doctorService = new DoctorService(doctorDAO);

        setLayout(new BorderLayout());

        String[] columnNames = {"Doctor id", "Name", "Consultation cost"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane,BorderLayout.CENTER);

        //Formulario para agregar medicos
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); //Espaciado entre componentes
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

        //Segunda columna
        gbc.gridx = 2;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Consultation cost:"), gbc);
        gbc.gridx = 3;
        JTextField costField = new JTextField(20);
        formPanel.add(costField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        formPanel.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 3;
        JTextField DNIField = new JTextField(20);
        formPanel.add(DNIField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Birth date(yyyy-MM-dd):"), gbc);
        gbc.gridx = 3;
        JTextField birthDateField = new JTextField(20);
        formPanel.add(birthDateField, gbc);

        // Botones
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton addButton = new JButton("Add doctor");
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
        JButton updateButton = new JButton("Update doctor");
        formPanel.add(updateButton, gbc);

        add(formPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e ->{
            String name =  nameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            double consultationCost = Double.parseDouble(costField.getText());
            int DNI = Integer.parseInt(DNIField.getText());
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
                JOptionPane.showMessageDialog(this, "Doctor created successfully", "Sucess", JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Error creating doctor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e ->{
            int selectedRow = table.getSelectedRow();
            if(selectedRow >= 0){
                int doctorID = (int) tableModel.getValueAt(selectedRow, 0);
                try{
                    doctorService.deleteDoctor(doctorID);
                    updateTable();
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error deleting doctor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(this, "Please select a doctor to delete", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateButton.addActionListener(e ->{
            int selectedRow = table.getSelectedRow();
            if(selectedRow >= 0){
                int doctorID = (int) tableModel.getValueAt(selectedRow, 0);
                try{
                    Doctor doctor = doctorService.getDoctor(doctorID);
                    goToEditDoctorPanel(doctor);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error fetching doctor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(this, "Please select a doctor to update", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        updateTable();
    }

    private void updateTable(){
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

    public void goToEditDoctorPanel(Doctor doctor){
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        EditDoctorPanel editDoctorPanel = new EditDoctorPanel(doctorService, parentFrame);
        editDoctorPanel.setDoctor(doctor);

        editDoctorPanel.addPropertyChangeListener("doctorUpdated", evt->{
            updateTable(); // Actualizar la tabla luego de editar
            parentFrame.getContentPane().removeAll();
            parentFrame.add(this);
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        parentFrame.getContentPane().removeAll();
        parentFrame.add(editDoctorPanel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}

