// Main.java
import database.DatabaseConnection;
import models.Patient;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse Main initialisiert die Anwendung und verbindet GUI und Datenbank.
 */
public class Main {
    public static void main(String[] args) {
        try {
            // FlatLaf Theme-Einstellungen
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Patientenverwaltung");
            DatabaseConnection dao = new DatabaseConnection();
            GUI gui = new GUI();

            // Aktionen verbinden
            gui.setSaveAction((data) -> {
                try {
                    String vorname = data[0];
                    String nachname = data[1];
                    String geschlecht = data[2];
                    String adresse = data[3];
                    int alter = Integer.parseInt(data[4]); // Konvertiere Alter zu Integer

                    dao.addPatient(new Patient(0, vorname, nachname, geschlecht, adresse, alter));
                    JOptionPane.showMessageDialog(frame, "Patient gespeichert!");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Ungültiges Alter! Bitte geben Sie eine Zahl ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Fehler beim Speichern des Patienten!", "Fehler", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            });

            gui.setRefreshAction(() -> {
                try {
                    List<Patient> patients = dao.getAllPatients();
                    List<Object[]> tableData = new ArrayList<>();
                    for (Patient patient : patients) {
                        tableData.add(new Object[]{
                                patient.getId(), patient.getVorname(), patient.getNachname(),
                                patient.getGeschlecht(), patient.getAdresse(), patient.getAlter()
                        });
                    }
                    gui.updateTable(tableData);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Fehler beim Abrufen der Patientendaten!", "Fehler", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            });

            // Fenster-Einstellungen
            frame.setContentPane(gui.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.setMinimumSize(new Dimension(800, 600));
            frame.setLocationRelativeTo(null); // Zentriert das Fenster auf dem Bildschirm
            frame.setVisible(true);

            // Initiale Aktualisierung
            gui.refreshAction.run();
        });
    }
}
