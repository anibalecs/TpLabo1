package graficInterface.panel;

import app.model.Doctor;
import app.service.DoctorService;
import javax.swing.*;
import java.awt.*;

public class EditDoctorPanel extends JPanel{
    private DoctorService doctorService;
    private JTextField nameField, lastNameField, emailField, consultationCostField, dniField, birthDateField;
    private JButton saveButton;
    private Doctor currentDoctor;
    private JFrame parentFrame;

    public EditDoctorPanel(DoctorService doctorService, JFrame parentFrame){
        this.doctorService = doctorService;
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Data
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        lastNameField = new JTextField(20);
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Consultation Cost:"), gbc);
        gbc.gridx = 1;
        consultationCostField = new JTextField(20);
        formPanel.add(consultationCostField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1;
        dniField = new JTextField(20);
        formPanel.add(dniField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Birth Date (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        birthDateField = new JTextField(20);
        formPanel.add(birthDateField, gbc);

        // Boton
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        saveButton = new JButton("Save Changes");
        formPanel.add(saveButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        saveButton.addActionListener(e -> saveChanges());
    }

    public void setDoctor(Doctor doctor){
        this.currentDoctor = doctor;
        nameField.setText(doctor.getName());
        lastNameField.setText(doctor.getLastName());
        emailField.setText(doctor.getEmail());
        consultationCostField.setText(String.valueOf(doctor.getConsultationCost()));
        dniField.setText(String.valueOf(doctor.getDNI()));
        birthDateField.setText(doctor.getBirthDate().toString());
    }

    private void saveChanges(){
        String name = nameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        double consultationCost = Double.parseDouble(consultationCostField.getText());
        int DNI = Integer.parseInt(dniField.getText());
        String birthDate = birthDateField.getText();

        currentDoctor.setName(name);
        currentDoctor.setLastName(lastName);
        currentDoctor.setEmail(email);
        currentDoctor.setConsultationCost(consultationCost);
        currentDoctor.setDNI(DNI);
        currentDoctor.setBirthDate(java.sql.Date.valueOf(birthDate));

        try{
            doctorService.updateDoctor(currentDoctor, currentDoctor.getDoctorID());
            JOptionPane.showMessageDialog(this, "Doctor updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            firePropertyChange("doctorUpdated", null, currentDoctor);

            //volver al doctor panel
            parentFrame.getContentPane().removeAll();
            DoctorPanel doctorPanel = new DoctorPanel();
            parentFrame.add(doctorPanel);
            parentFrame.revalidate();
            parentFrame.repaint();

        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Error updating doctor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

