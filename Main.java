
import database.PatientService;
import models.Patient;

import java.util.List;

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
    }

}
