package graficInterface.panel;

import javax.swing.*;
import java.awt.*;

public class DoctorMainPanel extends JPanel{
    private CardLayout cardLayout;
    private JPanel doctorPanel;

    public DoctorMainPanel(CardLayout cardLayout, JPanel mainPanel) throws Exception {
        this.cardLayout = cardLayout;
        setLayout(new BorderLayout());

        doctorPanel = new JPanel(new CardLayout());

        // Agrega paneles
        doctorPanel.add(new DoctorPanel(), "Doctors");
        doctorPanel.add(new PatientPanel(), "Patients");
        doctorPanel.add(new ShiftPanel(), "Shifts");
        doctorPanel.add(new ReportPanel(), "Reports");

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Manage");
        JMenuItem menuItemDoctor = new JMenuItem("Doctors");
        JMenuItem menuItemPatient = new JMenuItem("Patients");
        JMenuItem menuItemShift = new JMenuItem("Shifts");
        JMenuItem menuItemReport = new JMenuItem("Reports");
        menu.add(menuItemDoctor);
        menu.add(menuItemPatient);
        menu.add(menuItemShift);
        menu.add(menuItemReport);
        menuBar.add(menu);

        // Panel con el menú
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.add(menuBar, BorderLayout.NORTH);
        menuPanel.add(doctorPanel, BorderLayout.CENTER);

        add(menuPanel, BorderLayout.CENTER);

        // Listeners para cambiar entre paneles
        CardLayout doctorCardLayout = (CardLayout) doctorPanel.getLayout();
        menuItemDoctor.addActionListener(e -> doctorCardLayout.show(doctorPanel, "Doctors"));
        menuItemPatient.addActionListener(e -> doctorCardLayout.show(doctorPanel, "Patients"));
        menuItemShift.addActionListener(e -> doctorCardLayout.show(doctorPanel, "Shifts"));
        menuItemReport.addActionListener(e -> doctorCardLayout.show(doctorPanel, "Reports"));
    }
}

