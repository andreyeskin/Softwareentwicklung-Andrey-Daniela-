package DAO;

import database.DatabaseConnection;
import models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse PatientDAO dient als Datenzugriffsschicht für die Verwaltung von Patientendaten.
 * Sie bietet Methoden zum Abrufen, Hinzufügen, Aktualisieren und Löschen von Patienten sowie zum
 * Verwalten von zugehörigen Daten wie Geschlecht, Bundesland und Versicherung.
 */
public class PatientDAO {

    /**
     * Diese Methode ruft eine Liste aller Patienten aus der Datenbank ab.
     * @return Eine Liste von Patient-Objekten.
     * @throws SQLException Bei einem Fehler während der Datenbankabfrage.
     */
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

    /**
     * Ruft eine Liste aller Bundesländer aus der Datenbank ab.
     * @return Eine Liste der Namen der Bundesländer.
     * @throws SQLException Bei einem Fehler während der Datenbankabfrage.
     */
    public List<String> getBundeslandList() throws SQLException {
        List<String> bundeslaender = new ArrayList<>();
        String sql = "SELECT name FROM bundesland";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                bundeslaender.add(rs.getString("name"));
            }
        }
        return bundeslaender;
    }

    /**
     * Ruft die ID eines Bundeslandes anhand seines Namens ab.
     * @param name Der Name des Bundeslandes.
     * @return Die ID des Bundeslandes oder -1, wenn keines gefunden wurde.
     * @throws SQLException Bei einem Fehler während der Datenbankabfrage.
     */
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
        return -1;
    }

    /**
     * Ruft eine Liste aller Versicherungen aus der Datenbank ab.
     * @return Eine Liste der Namen der Versicherungen.
     * @throws SQLException Bei einem Fehler während der Datenbankabfrage.
     */
    public List<String> getInsuranceList() throws SQLException {
        List<String> insurances = new ArrayList<>();
        String sql = "SELECT name FROM insurance";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                insurances.add(rs.getString("name"));
            }
        }
        return insurances;
    }

    /**
     * Ruft die ID einer Versicherung anhand ihres Namens ab.
     * @param name Der Name der Versicherung.
     * @return Die ID der Versicherung oder -1, wenn keine gefunden wurde.
     * @throws SQLException Bei einem Fehler während der Datenbankabfrage.
     */
    public int getInsuranceIdByName(String name) throws SQLException {
        String sql = "SELECT id FROM insurance WHERE name = ?";
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

    /**
     * Ruft die ID eines Geschlechts anhand seines Namens ab.
     * @param name Der Name des Geschlechts.
     * @return Die ID des Geschlechts oder -1, wenn keines gefunden wurde.
     * @throws SQLException Bei einem Fehler während der Datenbankabfrage.
     */
    public int getGenderIdByName(String name) throws SQLException {
        String sql = "SELECT id FROM gender WHERE name = ?";
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

    /**
     * Ruft eine Liste aller Geschlechter aus der Datenbank ab.
     * @return Eine Liste der Namen der Geschlechter.
     * @throws SQLException Bei einem Fehler während der Datenbankabfrage.
     */
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

    /**
     * Hilfsmethode: Wandelt ein ResultSet in ein Patient-Objekt um.
     * @param rs Das ResultSet mit den Patientendaten.
     * @return Ein Patient-Objekt mit den ausgelesenen Daten.
     * @throws SQLException Bei einem Fehler während des Datenbankzugriffs.
     */
    private Patient mapToPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getInt("id"));
        patient.setAnrede(rs.getString("anrede"));
        patient.setVorname(rs.getString("vorname"));
        patient.setNachname(rs.getString("nachname"));
        patient.setGeburtstag(rs.getString("geburtstag"));
        patient.setSvnr(rs.getString("svnr"));
        patient.setInsuranceId(rs.getString("insurance_id"));
        patient.setTelefonnummer(rs.getString("telefonnummer"));
        patient.setStrasse(rs.getString("strasse"));
        patient.setPlz(rs.getString("plz"));
        patient.setOrt(rs.getString("ort"));
        patient.setGenderId(rs.getInt("gender_id"));
        patient.setBundeslandId(rs.getInt("bundesland_id"));
        return patient;
    }

    /**
     * Fügt einen neuen Patienten in die Datenbank ein.
     * @param patient Das hinzuzufügende Patient-Objekt.
     * @throws SQLException Bei einem Fehler während der Datenbankoperation.
     */
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
            stmt.setString(6, patient.getInsuranceId());
            stmt.setString(7, patient.getTelefonnummer());
            stmt.setString(8, patient.getStrasse());
            stmt.setString(9, patient.getPlz());
            stmt.setString(10, patient.getOrt());
            stmt.setInt(11, patient.getGenderId());
            stmt.setInt(12, patient.getBundeslandId());
            stmt.executeUpdate();
        }
    }

    /**
     * Aktualisiert die Daten eines vorhandenen Patienten in der Datenbank.
     * @param patient Das zu aktualisierende Patient-Objekt.
     * @throws SQLException Bei einem Fehler während der Datenbankoperation.
     */
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
            stmt.setString(6, patient.getInsuranceId());
            stmt.setString(7, patient.getTelefonnummer());
            stmt.setString(8, patient.getStrasse());
            stmt.setString(9, patient.getPlz());
            stmt.setString(10, patient.getOrt());
            stmt.setInt(11, patient.getGenderId());
            stmt.setInt(12, patient.getBundeslandId());
            stmt.setInt(13, patient.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Löscht einen Patienten anhand seiner ID aus der Datenbank.
     * @param id Die ID des zu löschenden Patienten.
     * @throws SQLException Bei einem Fehler während der Datenbankoperation.
     */
    public void deletePatient(int id) throws SQLException {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}