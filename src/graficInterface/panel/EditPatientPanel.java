package graficInterface.panel;

import app.model.Patient;
import app.service.PatientService;

import javax.swing.*;
import java.awt.*;

public class EditPatientPanel extends JPanel {
    private PatientService patientService;
    private JTextField nameField, lastNameField, emailField, dniField, birthDateField;
    private JButton saveButton;

    public EditPatientPanel(PatientService patientService) {
        this.patientService = patientService;
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
        formPanel.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1;
        dniField = new JTextField(20);
        formPanel.add(dniField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Birth Date (yyyy-mm-dd):"), gbc);
        gbc.gridx = 1;
        birthDateField = new JTextField(20);
        formPanel.add(birthDateField, gbc);

        // Botón Guardar
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        saveButton = new JButton("Save");
        formPanel.add(saveButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Listener para el botón Guardar
        saveButton.addActionListener(e -> {
            try {
                Patient patient = new Patient();
                patient.setName(nameField.getText());
                patient.setLastName(lastNameField.getText());
                patient.setEmail(emailField.getText());
                patient.setDNI(Integer.parseInt(dniField.getText()));
                patient.setBirthDate(java.sql.Date.valueOf(birthDateField.getText()));
                // Update patient info in the database
                //patientService.updatePatient(patient, patient.getDNI());
                JOptionPane.showMessageDialog(this, "Patient information updated successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating patient information: " + ex.getMessage());
            }
        });
    }

    public void setPatient(Patient patient) {
        nameField.setText(patient.getName());
        lastNameField.setText(patient.getLastName());
        emailField.setText(patient.getEmail());
        dniField.setText(String.valueOf(patient.getDNI()));
        birthDateField.setText(patient.getBirthDate().toString());
    }
}