package graficInterface.panel;

import app.model.Patient;
import app.service.PatientService;
import javax.swing.*;
import java.awt.*;

public class EditPatientPanel extends JPanel {
    private PatientService patientService;
    private JTextField nameField, lastNameField, emailField, dniField, birthDateField, numberField, number2Field, allergiesField;
    private JButton saveButton;
    private Patient currentPatient;

    public EditPatientPanel(PatientService patientService){
        this.patientService = patientService;
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

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Phone number:"), gbc);
        gbc.gridx = 1;
        numberField = new JTextField(20);
        formPanel.add(numberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Alternative number:"), gbc);
        gbc.gridx = 1;
        number2Field = new JTextField(20);
        formPanel.add(number2Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("Allergies:"), gbc);
        gbc.gridx = 1;
        allergiesField = new JTextField(20);
        formPanel.add(allergiesField, gbc);

        // Boton
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        saveButton = new JButton("Save");
        formPanel.add(saveButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        saveButton.addActionListener(e -> saveChanges());
    }

    public void setPatient(Patient patient){
        this.currentPatient = patient;
        nameField.setText(patient.getName());
        lastNameField.setText(patient.getLastName());
        emailField.setText(patient.getEmail());
        dniField.setText(String.valueOf(patient.getDNI()));
        birthDateField.setText(patient.getBirthDate().toString());
        numberField.setText(patient.getPhoneNumber());
        number2Field.setText(patient.getAlternativeNumber());
        allergiesField.setText(patient.getAllergies());
    }

    private void saveChanges(){
        if(currentPatient != null){
            String name = nameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            int DNI = Integer.parseInt(dniField.getText());
            String birthDate = birthDateField.getText();
            String phoneNumber = numberField.getText();
            String alternativeNumber = number2Field.getText();
            String allergies = allergiesField.getText();

            currentPatient.setName(name);
            currentPatient.setLastName(lastName);
            currentPatient.setEmail(email);
            currentPatient.setDNI(DNI);
            currentPatient.setBirthDate(java.sql.Date.valueOf(birthDate));
            currentPatient.setPhoneNumber(phoneNumber);
            currentPatient.setAlternativeNumber(alternativeNumber);
            currentPatient.setAllergies(allergies);

            try{
                patientService.updatePatient(currentPatient, currentPatient.getPatientID());
                JOptionPane.showMessageDialog(this, "Patient updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                firePropertyChange("patientUpdated", null, currentPatient);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Error updating patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            System.err.println("currentPatient is null. Cannot save changes.");
        }
    }
}