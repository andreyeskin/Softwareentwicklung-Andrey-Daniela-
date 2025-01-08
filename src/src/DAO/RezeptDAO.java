package DAO;

import database.DatabaseConnection;
import models.Rezept;
import models.Patient;
import models.Medikament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse RezeptDAO dient als Datenzugriffsschicht für die Verwaltung von Rezeptdaten.
 * Sie bietet Methoden zum Abrufen, Hinzufügen, Aktualisieren und Löschen von Rezepten sowie zum
 * Verwalten von zugehörigen Patienten- und Medikamentendaten.
 */
public class RezeptDAO {
    private Connection connection;

    /**
     * Konstruktor, um die Datenbankverbindung zu initialisieren.
     * @throws SQLException Bei einem Fehler während der Datenbankverbindung.
     */
    public RezeptDAO() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    /**
     * Methode, um alle Rezepte abzurufen.
     * @return Eine Liste von Rezept-Objekten.
     * @throws SQLException Bei einem Fehler während der Datenbankabfrage.
     */
    public List<Rezept> getAllRezepte() throws SQLException {
        List<Rezept> rezepte = new ArrayList<>();
        String query = "SELECT r.RezeptID, " +
                "CONCAT(p.vorname, ' ', p.nachname) AS PatientName, " +
                "m.Name AS MedikamentName, " +
                "r.Dosierung, r.Startdatum, r.Enddatum, r.Bemerkung " +
                "FROM rezept r " +
                "JOIN patients p ON r.PatientID = p.id " +
                "JOIN medikamente m ON r.MedikamentID = m.MedikamentID";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                rezepte.add(new Rezept(
                        rs.getInt("RezeptID"),
                        rs.getString("PatientName"),
                        rs.getString("MedikamentName"),
                        rs.getString("Dosierung"),
                        rs.getString("Startdatum"),
                        rs.getString("Enddatum"),
                        rs.getString("Bemerkung")
                ));
            }
        }
        return rezepte;
    }

    /**
     * Methode, um ein neues Rezept hinzuzufügen.
     * @param rezept Das hinzuzufügende Rezept-Objekt.
     * @throws SQLException Bei einem Fehler während der Datenbankoperation.
     */
    public void addRezept(Rezept rezept) throws SQLException {
        String query = "INSERT INTO rezept (PatientID, MedikamentID, Dosierung, Startdatum, Enddatum, Bemerkung) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, rezept.getPatientId());
            pstmt.setInt(2, rezept.getMedikamentId());
            pstmt.setString(3, rezept.getDosierung());
            pstmt.setString(4, rezept.getStartdatum());
            pstmt.setString(5, rezept.getEnddatum());
            pstmt.setString(6, rezept.getBemerkung());
            pstmt.executeUpdate();
        }
    }

    /**
     * Methode, um ein vorhandenes Rezept zu aktualisieren.
     * @param rezept Das zu aktualisierende Rezept-Objekt.
     * @throws SQLException Bei einem Fehler während der Datenbankoperation.
     */
    public void updateRezept(Rezept rezept) throws SQLException {
        String query = "UPDATE rezept SET PatientID = ?, MedikamentID = ?, Dosierung = ?, Startdatum = ?, Enddatum = ?, Bemerkung = ? WHERE RezeptID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, rezept.getPatientId());
            stmt.setInt(2, rezept.getMedikamentId());
            stmt.setString(3, rezept.getDosierung());
            stmt.setString(4, rezept.getStartdatum());
            stmt.setString(5, rezept.getEnddatum());
            stmt.setString(6, rezept.getBemerkung());
            stmt.setInt(7, rezept.getRezeptId());
            stmt.executeUpdate();
        }
    }

    /**
     * Methode, um ein Rezept zu löschen.
     * @param rezeptId Die ID des zu löschenden Rezepts.
     * @throws SQLException Bei einem Fehler während der Datenbankoperation.
     */
    public void deleteRezept(int rezeptId) throws SQLException {
        String query = "DELETE FROM rezept WHERE RezeptID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, rezeptId);
            stmt.executeUpdate();
        }
    }

    /**
     * Methode, um alle Patienten abzurufen.
     * @return Eine Liste von Patient-Objekten.
     * @throws SQLException Bei einem Fehler während der Datenbankabfrage.
     */
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT id, vorname, nachname FROM patients";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("id"),
                        rs.getString("vorname"),
                        rs.getString("nachname")
                ));
            }
        }
        return patients;
    }

    /**
     * Methode, um alle Medikamente abzurufen.
     * @return Eine Liste von Medikament-Objekten.
     * @throws SQLException Bei einem Fehler während der Datenbankabfrage.
     */
    public List<Medikament> getAllMedikamente() throws SQLException {
        List<Medikament> medikamente = new ArrayList<>();
        String query = "SELECT MedikamentID, Name FROM medikamente";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                medikamente.add(new Medikament(
                        rs.getInt("MedikamentID"),
                        rs.getString("Name"),
                        null
                ));
            }
        }
        return medikamente;
    }
}
