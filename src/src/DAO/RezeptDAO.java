package DAO;

import database.DatabaseConnection;
import models.Rezept;
import models.Patient;
import models.Medikament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RezeptDAO {
    private Connection connection;

    // Konstruktor, um die Datenbankverbindung zu initialisieren
    public RezeptDAO() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    // Methode, um alle Rezepte abzurufen
    public List<Rezept> getAllRezepte() throws SQLException {
        List<Rezept> rezepte = new ArrayList<>();
        String query = "SELECT r.RezeptID, " +
                "CONCAT(p.vorname, ' ', p.nachname) AS PatientName, " +
                "m.Name AS MedikamentName, " +
                "r.Dosierung, r.Startdatum, r.Enddatum, r.Bemerkung " +
                "FROM rezept r " +
                "JOIN patients p ON r.PatientID = p.id " + // Используем r.PatientID и p.id
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

    // Methode, um ein neues Rezept hinzuzufügen
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

    // Methode, um ein vorhandenes Rezept zu aktualisieren
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


    // Methode, um ein Rezept zu löschen
    public void deleteRezept(int rezeptId) throws SQLException {
        String query = "DELETE FROM rezept WHERE RezeptID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, rezeptId);
            stmt.executeUpdate();
        }
    }

    // Methode, um alle Patienten abzurufen
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT id, vorname, nachname FROM patients"; // Используем id вместо PatientID
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("id"), // Используем id
                        rs.getString("vorname"),
                        rs.getString("nachname")
                ));
            }
        }
        return patients;
    }

    // Methode, um alle Medikamente abzurufen
    public List<Medikament> getAllMedikamente() throws SQLException {
        List<Medikament> medikamente = new ArrayList<>();
        String query = "SELECT MedikamentID, Name FROM medikamente";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                // Ein Medikament-Objekt aus den Datenbankwerten erstellen
                medikamente.add(new Medikament(
                        rs.getInt("MedikamentID"),
                        rs.getString("Name"),
                        null // Beschreibung ist für das Rezept nicht notwendig
                ));
            }
        }
        return medikamente;
    }
}