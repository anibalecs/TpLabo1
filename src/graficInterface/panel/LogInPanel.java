package graficInterface.panel;

import javax.swing.*;
import java.awt.*;

public class LogInPanel extends JPanel{
    public LogInPanel(CardLayout cardLayout, JPanel mainPanel){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Boton
        JButton doctorButton = new JButton("Doctor");
        doctorButton.setPreferredSize(new Dimension(100, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(doctorButton, gbc);

        // Boton
        JButton patientButton = new JButton("Patient");
        patientButton.setPreferredSize(new Dimension(100, 40));
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(patientButton, gbc);

        doctorButton.addActionListener(e -> cardLayout.show(mainPanel, "DoctorPanel"));
        patientButton.addActionListener(e -> cardLayout.show(mainPanel, "PatientPanel"));
    }
}