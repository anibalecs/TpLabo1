package graficInterface.panel;

import javax.swing.*;
import java.awt.*;

public class PatientPanel extends JPanel {
    public PatientPanel() {
        setLayout(new BorderLayout());

        // Tabla de pacientes
        String[] columnNames = {"ID", "Name"};
        Object[][] data = {
                // Aquí se agregarían los datos de los pacientes
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Formulario para agregar pacientes
        JPanel formPanel = new JPanel(new GridLayout(8, 2));
        formPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);
        formPanel.add(new JLabel("Last name:"));
        JTextField lastNameField = new JTextField();
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        formPanel.add(emailField);
        formPanel.add(new JLabel("DNI:"));
        JTextField DNIField = new JTextField();
        formPanel.add(DNIField);
        formPanel.add(new JLabel("Birth Date:"));
        JTextField birthDateField = new JTextField();
        formPanel.add(birthDateField);
        JButton addButton = new JButton("Add patient");
        formPanel.add(addButton);
        formPanel.add(new JLabel()); // Empty label to keep the button centered

        add(formPanel, BorderLayout.SOUTH);

        // Listener para el botón de agregar paciente
        addButton.addActionListener(e -> {
            // Aquí se agregaría la lógica para agregar un paciente a la base de datos
            // y actualizar la tabla
        });
    }
}
