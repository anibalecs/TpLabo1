package graficInterface.panel;

import app.model.Doctor;
import app.model.Patient;

import javax.swing.*;
import java.awt.*;

public class ShiftPanel extends JPanel{
    public ShiftPanel() {
        setLayout(new BorderLayout());

        // Tabla de turnos
        String[] columnNames = {"ID", "Doctor", "Patient", "Date and time"};
        Object[][] data = {
                // Aquí se agregarían los datos de los turnos
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Formulario para agregar turnos
        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        formPanel.add(new JLabel("Doctor:"));
        JComboBox<Doctor> doctorComboBox = new JComboBox<>();
        formPanel.add(doctorComboBox);
        formPanel.add(new JLabel("Patinet:"));
        JComboBox<Patient> patientComboBox = new JComboBox<>();
        formPanel.add(patientComboBox);
        formPanel.add(new JLabel("Date and time:"));
        JTextField dateTimeField = new JTextField();
        formPanel.add(dateTimeField);
        JButton addButton = new JButton("Add shift");
        formPanel.add(addButton);

        add(formPanel, BorderLayout.SOUTH);

        // Listener para el botón de agregar turno
        addButton.addActionListener(e -> {
            // Aquí se agregaría la lógica para agregar un turno a la base de datos
            // y actualizar la tabla
        });
    }
}
