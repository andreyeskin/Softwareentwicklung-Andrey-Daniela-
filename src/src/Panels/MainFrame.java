package Panels;

import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JButton exportButton;

    public MainFrame() throws SQLException {
        setTitle("Krankenhausverwaltung");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menüleiste
        JMenuBar menuBar = new JMenuBar();





        setJMenuBar(menuBar);

        // Listener für das Schließen des Fensters
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DatabaseConnection.closeConnection(); // Verbindung schließen
            }
        });



        // CardLayout für Module
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);



        // Panels für Module
        // Panels erstellen
        PatientDiagnosenPanel patientDiagnosenPanel = new PatientDiagnosenPanel();
        PatientenPanel patientenPanel = new PatientenPanel(patientDiagnosenPanel); // Übergabe des Panels
        DiagnosenPanel diagnosenPanel = new DiagnosenPanel();
        MedikamentePanel medikamentePanel = new MedikamentePanel();
        RezeptePanel rezeptePanel = new RezeptePanel();


// Установить связь между панелями
        patientenPanel.setRezeptePanel(rezeptePanel);

        mainPanel.add(patientenPanel, "Patienten");
        mainPanel.add(diagnosenPanel, "Diagnosen");
        mainPanel.add(patientDiagnosenPanel, "Patient-Diagnosen");




        exportButton = new JButton("Exportieren");

        // Verbindung zwischen DiagnosenPanel und PatientDiagnosenPanel herstellen
        diagnosenPanel.setPatientDiagnosenPanel(patientDiagnosenPanel);



        // In der Hauptklasse mit JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Patienten", patientenPanel);
        tabbedPane.addTab("Diagnosen", diagnosenPanel);
        tabbedPane.addTab("Patient-Diagnosen", patientDiagnosenPanel);
        add(tabbedPane);
        tabbedPane.addTab("Medikamente", medikamentePanel);
        tabbedPane.addTab("Rezepte", rezeptePanel);


        // Füge das TabbedPane zum JFrame hinzu
        add(tabbedPane, BorderLayout.CENTER);



    }


}