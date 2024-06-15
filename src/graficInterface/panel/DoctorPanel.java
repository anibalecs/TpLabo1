package graficInterface.panel;

import java.awt.*;
import javax.swing.*;

public class DoctorPanel extends JPanel {
    public DoctorPanel() {
        setLayout(new BorderLayout());

        // Tabla de médicos
        String[] columnNames = {"ID", "Name", "Consultation cost"};
        Object[][] data = {
                // Aquí se agregarían los datos de los médicos
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Formulario para agregar médicos
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Primera columna
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Consultation cost:"), gbc);
        gbc.gridx = 1;
        JTextField costField = new JTextField(20);
        formPanel.add(costField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Last name:"), gbc);
        gbc.gridx = 1;
        JTextField lastNameField = new JTextField(20);
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("User name:"), gbc);
        gbc.gridx = 1;
        JTextField userNameField = new JTextField(20);
        formPanel.add(userNameField, gbc);

        // Segunda columna
        gbc.gridx = 2;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 3;
        JTextField passwordField = new JTextField(20);
        formPanel.add(passwordField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 3;
        JTextField emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        formPanel.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 3;
        JTextField DNIField = new JTextField(20);
        formPanel.add(DNIField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Birth date:"), gbc);
        gbc.gridx = 3;
        JTextField birthDateField = new JTextField(20);
        formPanel.add(birthDateField, gbc);

        // Botón de agregar médico
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton addButton = new JButton("Add doctor");
        formPanel.add(addButton, gbc);

        add(formPanel, BorderLayout.SOUTH);

        // Listener para el botón de agregar médico
        addButton.addActionListener(e -> {
            // Aquí se agregaría la lógica para agregar un médico a la base de datos
            // y actualizar la tabla
        });
    }
}