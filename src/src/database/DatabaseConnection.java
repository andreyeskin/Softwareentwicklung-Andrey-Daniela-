package database;

import models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse DatabaseConnection bietet Methoden für den Zugriff auf die PersonenDaten-Datenbank.
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/PersonenVerzeichnisDB";
    private static final String USER = "root";
    private static final String PASSWORD = "daniyfelI123";

    // Methode zum Herstellen einer Verbindung zur Datenbank
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Methode zum Hinzufügen eines neuen Patienten
    public void addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO PersonenDaten (anrede, vorname, nachname, geburtsdatum, svnr, versicherung, telefonnummer, strasse, plz, ort, bundesland) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Validierung der Patientendaten
            if (patient.getVorname() == null || patient.getNachname() == null || patient.getAnrede() == null ||
                    patient.getGeburtsdatum() == null || patient.getSvnr() == null || patient.getVersicherung() == null) {
                throw new IllegalArgumentException("Die Patientendaten sind ungültig.");
            }

            // Parameter für die SQL-Abfrage festlegen
            stmt.setString(1, patient.getAnrede());
            stmt.setString(2, patient.getVorname());
            stmt.setString(3, patient.getNachname());
            stmt.setDate(4, Date.valueOf(patient.getGeburtsdatum())); // Geburtsdatum im Format yyyy-MM-dd
            stmt.setString(5, patient.getSvnr());
            stmt.setString(6, patient.getVersicherung());
            stmt.setString(7, patient.getTelefonnummer());
            stmt.setString(8, patient.getStrasse());
            stmt.setString(9, patient.getPlz());
            stmt.setString(10, patient.getOrt());
            stmt.setString(11, patient.getBundesland());
            stmt.executeUpdate();

            System.out.println("Patient erfolgreich gespeichert: " + patient);
        } catch (SQLException e) {
            System.err.println("Fehler beim Speichern des Patienten: " + e.getMessage());
            throw e;
        }
    }

    // Methode zum Abrufen aller Patienten
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM PersonenDaten";
        try (Connection connection = connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("id"),
                        rs.getString("anrede"),
                        rs.getString("vorname"),
                        rs.getString("nachname"),
                        rs.getDate("geburtsdatum").toLocalDate(),
                        rs.getString("svnr"),
                        rs.getString("versicherung"),
                        rs.getString("telefonnummer"),
                        rs.getString("strasse"),
                        rs.getString("plz"),
                        rs.getString("ort"),
                        rs.getString("bundesland")
                ));
            }
        }
        return patients;
    }

    // Methode zum Testen der Verbindung
    public static void testConnection() {
        try (Connection connection = connect()) {
            System.out.println("Erfolgreiche Verbindung zur Datenbank.");
        } catch (SQLException e) {
            System.err.println("Fehler bei der Verbindung: " + e.getMessage());
        }
    }
}
