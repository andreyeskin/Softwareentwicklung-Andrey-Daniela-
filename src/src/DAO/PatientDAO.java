package DAO;

import database.DatabaseConnection;
import models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {



    // Patienten abrufen (nur mit IDs)
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        String sql = "SELECT id, anrede, vorname, nachname, geburtstag, svnr, insurance_id, " +
                "telefonnummer, strasse, plz, ort, gender_id, bundesland_id FROM patients";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                patientList.add(mapToPatient(rs));
            }
        }
        return patientList;
    }

    public List<String> getBundeslandList() throws SQLException {
        List<String> bundeslaender = new ArrayList<>();
        String sql = "SELECT name FROM bundesland"; // Tabellenname und Spaltenname anpassen, falls nötig
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                bundeslaender.add(rs.getString("name")); // Spaltenname anpassen
            }
        }
        return bundeslaender;
    }
    public int getBundeslandIdByName(String name) throws SQLException {
        String sql = "SELECT id FROM bundesland WHERE name = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1; // Falls kein Bundesland gefunden wurde
    }
    public List<String> getInsuranceList() throws SQLException {
        List<String> insurances = new ArrayList<>();
        String sql = "SELECT name FROM insurance"; // Passen Sie 'insurance' an den Tabellennamen in Ihrer Datenbank an.
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                insurances.add(rs.getString("name")); // Passen Sie 'name' an die Spalte in Ihrer Datenbank an.
            }
        }
        return insurances;
    }

    public int getInsuranceIdByName(String name) throws SQLException {
        String sql = "SELECT id FROM insurance WHERE name = ?"; // 'insurance' und 'id' an Ihre Datenbankstruktur anpassen
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id"); // 'id' an die Spaltenbezeichnung in Ihrer Tabelle anpassen
                }
            }
        }
        return -1; // -1 zurückgeben, wenn keine ID gefunden wurde
    }
    public int getGenderIdByName(String name) throws SQLException {
        String sql = "SELECT id FROM gender WHERE name = ?"; // 'gender' und 'id' anpassen
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }

    public List<String> getGenderList() throws SQLException {
        List<String> genderList = new ArrayList<>();
        String sql = "SELECT name FROM gender";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                genderList.add(rs.getString("name"));
            }
        }
        return genderList;
    }


    // Hilfsmethode: ResultSet in Patient-Objekt umwandeln
    private Patient mapToPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getInt("id"));
        patient.setAnrede(rs.getString("anrede"));
        patient.setVorname(rs.getString("vorname"));
        patient.setNachname(rs.getString("nachname"));
        patient.setGeburtstag(rs.getString("geburtstag"));
        patient.setSvnr(rs.getString("svnr"));
        patient.setInsuranceId(rs.getString("insurance_id")); // Versicherung ID
        patient.setTelefonnummer(rs.getString("telefonnummer"));
        patient.setStrasse(rs.getString("strasse"));
        patient.setPlz(rs.getString("plz"));
        patient.setOrt(rs.getString("ort"));
        patient.setGenderId(rs.getInt("gender_id")); // Geschlecht ID
        patient.setBundeslandId(rs.getInt("bundesland_id")); // Bundesland ID
        return patient;
    }

    // Patient hinzufügen
    public void addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (anrede, vorname, nachname, geburtstag, svnr, insurance_id, " +
                "telefonnummer, strasse, plz, ort, gender_id, bundesland_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patient.getAnrede());
            stmt.setString(2, patient.getVorname());
            stmt.setString(3, patient.getNachname());
            stmt.setString(4, patient.getGeburtstag());
            stmt.setString(5, patient.getSvnr());
            stmt.setString(6, patient.getInsuranceId()); // Versicherung ID
            stmt.setString(7, patient.getTelefonnummer());
            stmt.setString(8, patient.getStrasse());
            stmt.setString(9, patient.getPlz());
            stmt.setString(10, patient.getOrt());
            stmt.setInt(11, patient.getGenderId()); // Geschlecht ID
            stmt.setInt(12, patient.getBundeslandId()); // Bundesland ID
            stmt.executeUpdate();
        }
    }

    // Patient aktualisieren
    public void updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET anrede = ?, vorname = ?, nachname = ?, geburtstag = ?, svnr = ?, " +
                "insurance_id = ?, telefonnummer = ?, strasse = ?, plz = ?, ort = ?, gender_id = ?, " +
                "bundesland_id = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patient.getAnrede());
            stmt.setString(2, patient.getVorname());
            stmt.setString(3, patient.getNachname());
            stmt.setString(4, patient.getGeburtstag());
            stmt.setString(5, patient.getSvnr());
            stmt.setString(6, patient.getInsuranceId()); // Versicherung ID
            stmt.setString(7, patient.getTelefonnummer());
            stmt.setString(8, patient.getStrasse());
            stmt.setString(9, patient.getPlz());
            stmt.setString(10, patient.getOrt());
            stmt.setInt(11, patient.getGenderId()); // Geschlecht ID
            stmt.setInt(12, patient.getBundeslandId()); // Bundesland ID
            stmt.setInt(13, patient.getId());
            stmt.executeUpdate();
        }
    }

    // Patient löschen
    public void deletePatient(int id) throws SQLException {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

