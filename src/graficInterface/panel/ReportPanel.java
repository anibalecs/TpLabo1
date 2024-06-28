package graficInterface.panel;

import app.model.ReportData;
import app.service.ShiftService;
import dataBase.DAO.ShiftDAO;
import dataBase.DAO.ShiftDAOImpl;
import dataBase.DataBaseConfig;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class ReportPanel extends JPanel{
    private ShiftService shiftService;
    private JTextField doctorIDField;
    private JTextField startDateField;
    private JTextField endDateField;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel totalQueriesLabel;
    private JLabel totalAmountLabel;

    public ReportPanel(){
        ShiftDAO shiftDAO = new ShiftDAOImpl(DataBaseConfig.connect());
        this.shiftService = new ShiftService(shiftDAO);

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Doctor ID:"));
        doctorIDField = new JTextField();
        inputPanel.add(doctorIDField);

        inputPanel.add(new JLabel("Start Date (yyyy-mm-dd):"));
        startDateField = new JTextField();
        inputPanel.add(startDateField);

        inputPanel.add(new JLabel("End Date (yyyy-mm-dd):"));
        endDateField = new JTextField();
        inputPanel.add(endDateField);

        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(e -> generateReport());
        inputPanel.add(generateReportButton);

        add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Date", "Queries", "Amount Charged"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel totalsPanel = new JPanel(new GridLayout(1, 2));
        totalQueriesLabel = new JLabel("Total Queries: 0");
        totalAmountLabel = new JLabel("Total Amount Charged: 0");
        totalsPanel.add(totalQueriesLabel);
        totalsPanel.add(totalAmountLabel);
        add(totalsPanel, BorderLayout.SOUTH);
    }

    private void generateReport(){
        try{
            int doctorID = Integer.parseInt(doctorIDField.getText());
            String startDateStr = startDateField.getText() + " 00:00:00";
            String endDateStr = endDateField.getText() + " 23:59:59";
            Timestamp startDate = Timestamp.valueOf(startDateStr);
            Timestamp endDate = Timestamp.valueOf(endDateStr);

            List<ReportData> reportDataList = shiftService.getReportDataForDoctor(doctorID, startDate, endDate);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tableModel.setRowCount(0); //para limpiar la tabla antes de agregar una nueva

            int totalQueries = 0;
            double totalAmountCharged = 0.0;

            for(ReportData data : reportDataList){
                Object[] row ={
                        dateFormat.format(data.getDate()),
                        data.getNumberOfQueries(),
                        data.getAmountCharged()
                };
                tableModel.addRow(row);

                totalQueries += data.getNumberOfQueries();
                totalAmountCharged += data.getAmountCharged();
            }

            totalQueriesLabel.setText("Total Queries: " + totalQueries);
            totalAmountLabel.setText("Total Amount Charged: " + totalAmountCharged);

        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
