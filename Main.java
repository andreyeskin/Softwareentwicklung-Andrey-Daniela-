import database.UserDatabaseConnection;
import database.DatabaseConnection;
import models.Patient;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // FlatLaf Theme
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // LoginGUI erstellen
            LoginGUI loginGUI = new LoginGUI();
            UserDatabaseConnection userDb = new UserDatabaseConnection(); // Verbindung zur BenutzerDatenbank

            // Login-Aktion
            loginGUI.setLoginAction((username, password) -> {
                if (userDb.authenticate(username, password).isPresent()) {
                    // Login erfolgreich
                    loginGUI.close();
                    showMainGUI(); // Haupt-GUI anzeigen
                } else {
                    // Login fehlgeschlagen
                    loginGUI.showMessage("Ungültiger Benutzername oder Passwort!");
                }
            });

            loginGUI.show(); // Login-Fenster anzeigen
        });
    }

    // Haupt-GUI anzeigen
    private static void showMainGUI() {
        JFrame frame = new JFrame("Patientenverwaltung");
        DatabaseConnection dao = new DatabaseConnection();
        GUI gui = new GUI();

        gui.setSaveAction((data) -> {
            try {
                String anrede = data[0];
                String vorname = data[1];
                String nachname = data[2];
                LocalDate geburtsdatum = LocalDate.parse(data[3]); // Konvertiere zu LocalDate (Format: yyyy-MM-dd)
                String svnr = data[4];
                String versicherung = data[5];
                String telefonnummer = data[6];
                String strasse = data[7];
                String plz = data[8];
                String ort = data[9];
                String bundesland = data[10];

                dao.addPatient(new Patient(0, anrede, vorname, nachname, geburtsdatum, svnr, versicherung, telefonnummer, strasse, plz, ort, bundesland));
                JOptionPane.showMessageDialog(frame, "Patient gespeichert!");
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
                            patient.getId(),
                            patient.getAnrede(),
                            patient.getVorname(),
                            patient.getNachname(),
                            patient.getGeburtsdatum(),
                            patient.getSvnr(),
                            patient.getVersicherung(),
                            patient.getTelefonnummer(),
                            patient.getStrasse(),
                            patient.getPlz(),
                            patient.getOrt(),
                            patient.getBundesland()
                    });
                }
                gui.updateTable(tableData);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Fehler beim Abrufen der Patientendaten!", "Fehler", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });

        frame.setContentPane(gui.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gui.refreshAction.run();
    }
}
