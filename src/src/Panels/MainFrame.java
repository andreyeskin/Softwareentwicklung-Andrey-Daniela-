package Panels;

import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * Die Klasse MainFrame repräsentiert das Hauptfenster der Krankenhausverwaltungsanwendung.
 * Sie verwendet ein CardLayout und ein JTabbedPane, um verschiedene Module wie Patienten, Diagnosen usw. zu verwalten.
 */
public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    /**
     * Konstruktor, um das Hauptfenster der Anwendung zu initialisieren.
     * @throws SQLException Wenn ein Fehler bei der Datenbankverbindung auftritt.
     */
    public MainFrame() throws SQLException {
        setTitle("Krankenhausverwaltung");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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
        PatientDiagnosenPanel patientDiagnosenPanel = new PatientDiagnosenPanel();
        PatientenPanel patientenPanel = new PatientenPanel(patientDiagnosenPanel); // Übergabe des Panels
        DiagnosenPanel diagnosenPanel = new DiagnosenPanel();
        MedikamentePanel medikamentePanel = new MedikamentePanel();
        RezeptePanel rezeptePanel = new RezeptePanel();

        // Verbindung zwischen den Panels herstellen
        patientenPanel.setRezeptePanel(rezeptePanel);

        // Hinzufügen der Panels zum CardLayout
        mainPanel.add(patientenPanel, "Patienten");
        mainPanel.add(diagnosenPanel, "Diagnosen");
        mainPanel.add(patientDiagnosenPanel, "Patient-Diagnosen");

        // JTabbedPane für die Module
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Patienten", patientenPanel);
        tabbedPane.addTab("Diagnosen", diagnosenPanel);
        tabbedPane.addTab("Patient-Diagnosen", patientDiagnosenPanel);
        tabbedPane.addTab("Medikamente", medikamentePanel);
        tabbedPane.addTab("Rezepte", rezeptePanel);

        // Hinzufügen des TabbedPane zum JFrame
        add(tabbedPane, BorderLayout.CENTER);
    }
}
