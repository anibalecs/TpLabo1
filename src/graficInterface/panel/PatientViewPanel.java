package graficInterface.panel;

import javax.swing.*;
import java.awt.*;

public class PatientViewPanel extends JPanel {
    public PatientViewPanel() {
        setLayout(new BorderLayout());

        // Tabla de turnos del paciente
        String[] columnNames = {"Date", "Hour", "Doctor"};
        Object[][] data = {
                // Aquí egregar los datos de los turnos del paciente
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Formulario seleccionar el paciente
        JPanel formPanel = new JPanel(new GridLayout(2, 1));
        formPanel.add(new JLabel("Patient id:"));
        JTextField patientIdField = new JTextField();
        formPanel.add(patientIdField);

        JButton viewButton = new JButton("See shifts");
        formPanel.add(viewButton);

        add(formPanel, BorderLayout.NORTH);

        // Listener para el botón de ver turnos
        viewButton.addActionListener(e -> {
            // Aquí agregar paraobtener los turnos del paciente de la base de datos
            // y actualizar la tabla
        });
    }
}
