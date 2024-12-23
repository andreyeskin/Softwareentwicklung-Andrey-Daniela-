import com.formdev.flatlaf.FlatLightLaf;
import database.PatientService;
import models.Patient;

import javax.swing.*;
import java.util.List;
import com.formdev.flatlaf.FlatLightLaf;

/**
     * Die Hauptklasse für das Programm.
     */
    public class Main {
        public static void main(String[] args) {
            PatientService service = new PatientService();

            // Einen Patienten hinzufügen
            Patient newPatient = new Patient(0, "Max Mustermann", 30, "Musterstraße 123");
            service.addPatient(newPatient);

            // Alle Patienten abrufen
            List<Patient> patients = service.getAllPatients();
            for (Patient patient : patients) {
                System.out.println(patient);
            }

            // Einen Patienten aktualisieren
            if (!patients.isEmpty()) {
                Patient patientToUpdate = patients.get(0);
                patientToUpdate.setName("Maximilian Muster");
                service.updatePatient(patientToUpdate);
            }

            // Einen Patienten löschen
            if (!patients.isEmpty()) {
                service.deletePatient(patients.get(0).getId());
            }
        }

        // FlatLaf aktivieren
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            System.err.println("Failed to initialize FlatLaf.");
            e.printStackTrace();
        }

        // GUI starten
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Meine Anwendung");
            GUI gui = new GUI(); // GUI-Objekt erstellen
            frame.setContentPane(gui.getMainPanel()); // Panel der GUI setzen
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
    }

}
