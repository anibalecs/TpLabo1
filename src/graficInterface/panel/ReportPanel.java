package graficInterface.panel;

import javax.swing.*;
import java.awt.*;

public class ReportPanel extends JPanel {
    public ReportPanel() {
        setLayout(new BorderLayout());

        // Panel de selección de médico y fechas
        JPanel selectionPanel = new JPanel(new GridLayout(4, 2));
        selectionPanel.add(new JLabel("Doctor:"));
        JComboBox<String> doctorComboBox = new JComboBox<>(new String[]{
                "Doctor 1", "Doctor 2", "Doctor 3" // Aquí agregar los nombres de los médicos desde la base de datos
        });
        selectionPanel.add(doctorComboBox);

        selectionPanel.add(new JLabel("Start date:"));
        JPanel startDatePanel = createDatePickerPanel();
        selectionPanel.add(startDatePanel);

        selectionPanel.add(new JLabel("End date:"));
        JPanel endDatePanel = createDatePickerPanel();
        selectionPanel.add(endDatePanel);

        JButton generateButton = new JButton("Search report");
        selectionPanel.add(generateButton);

        add(selectionPanel, BorderLayout.NORTH);

        // Tabla de reportes
        String[] columnNames = {"Date", "Amount charged", "Number of queries"};
        Object[][] data = {
                // Aquí se agregarían los datos del reporte
        };
        JTable reportTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(reportTable);
        add(scrollPane, BorderLayout.CENTER);

        // Listener para el botón de generar reporte
        generateButton.addActionListener(e -> {
            String selectedDoctor = (String) doctorComboBox.getSelectedItem();

            String startDay = (String) ((JComboBox<?>) startDatePanel.getComponent(0)).getSelectedItem();
            String startMonth = (String) ((JComboBox<?>) startDatePanel.getComponent(1)).getSelectedItem();
            String startYear = (String) ((JComboBox<?>) startDatePanel.getComponent(2)).getSelectedItem();
            String startDate = startYear + "-" + startMonth + "-" + startDay;

            String endDay = (String) ((JComboBox<?>) endDatePanel.getComponent(0)).getSelectedItem();
            String endMonth = (String) ((JComboBox<?>) endDatePanel.getComponent(1)).getSelectedItem();
            String endYear = (String) ((JComboBox<?>) endDatePanel.getComponent(2)).getSelectedItem();
            String endDate = endYear + "-" + endMonth + "-" + endDay;

            // Aquí se agrega la lógica para obtener los datos del reporte desde la base de datos
            // y actualizar la tabla
        });
    }

    private JPanel createDatePickerPanel() {
        JPanel datePanel = new JPanel(new GridLayout(1, 3));

        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.format("%02d", i + 1);
        }
        JComboBox<String> dayComboBox = new JComboBox<>(days);
        datePanel.add(dayComboBox);

        String[] months = new String[12];
        for (int i = 0; i < 12; i++) {
            months[i] = String.format("%02d", i + 1);
        }
        JComboBox<String> monthComboBox = new JComboBox<>(months);
        datePanel.add(monthComboBox);

        String[] years = new String[50];
        for (int i = 0; i < 50; i++) {
            years[i] = String.valueOf(2023 + i);
        }
        JComboBox<String> yearComboBox = new JComboBox<>(years);
        datePanel.add(yearComboBox);

        return datePanel;
    }
}
