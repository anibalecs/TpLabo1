package graficInterface.panel;

import app.model.Doctor;
import app.service.DoctorService;

import javax.swing.*;
import java.awt.*;

public class EditDoctorPanel extends JPanel {
    private DoctorService doctorService;
    private JTextField nameField, lastNameField, emailField, consultationCostField, dniField, birthDateField;
    private JButton saveButton;

    public EditDoctorPanel(DoctorService doctorService) {
        this.doctorService = doctorService;
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels y Campos de Texto
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
        formPanel.add(new JLabel("Birth Date (yyyy-mm-dd):"), gbc);
        gbc.gridx = 1;
        birthDateField = new JTextField(20);
        formPanel.add(birthDateField, gbc);

        // Botón Guardar
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        saveButton = new JButton("Save");
        formPanel.add(saveButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Listener para el botón Guardar
        saveButton.addActionListener(e -> {
            try {
                Doctor doctor = new Doctor();
                doctor.setName(nameField.getText());
                doctor.setLastName(lastNameField.getText());
                doctor.setEmail(emailField.getText());
                doctor.setConsultationCost(Double.parseDouble(consultationCostField.getText()));
                doctor.setDNI(Integer.parseInt(dniField.getText()));
                doctor.setBirthDate(java.sql.Date.valueOf(birthDateField.getText()));
                // Update doctor info in the database
                doctorService.updateDoctor(doctor, doctor.getDNI());
                JOptionPane.showMessageDialog(this, "Doctor information updated successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating doctor information: " + ex.getMessage());
            }
        });
    }

    public void setDoctor(Doctor doctor) {
        nameField.setText(doctor.getName());
        lastNameField.setText(doctor.getLastName());
        emailField.setText(doctor.getEmail());
        consultationCostField.setText(String.valueOf(doctor.getConsultationCost()));
        dniField.setText(String.valueOf(doctor.getDNI()));
        birthDateField.setText(doctor.getBirthDate().toString());
    }
}



//revisar
