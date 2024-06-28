package graficInterface;

import graficInterface.panel.*;
import javax.swing.*;
import java.awt.*;

public class MedicalTurnerApp{
    public static void startPanel(){
        SwingUtilities.invokeLater(()->{
            JFrame frame = new JFrame("Medical Turner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());

            //paneles
            CardLayout cardLayout = new CardLayout();
            JPanel mainPanel = new JPanel(cardLayout);
            frame.add(mainPanel, BorderLayout.CENTER);

            //Agrega paneles
            mainPanel.add(new LogInPanel(cardLayout, mainPanel), "LogInPanel");
            mainPanel.add(new DoctorMainPanel(cardLayout, mainPanel), "DoctorPanel");
            mainPanel.add(new PatientViewPanel(), "PatientPanel");

            frame.setVisible(true);

            //login
            cardLayout.show(mainPanel, "LogInPanel");
        });
    }
}
