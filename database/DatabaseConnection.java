package database;

import models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse DatabaseConnection bietet Methoden für den Zugriff auf die Patientendatenbank.
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/patientdb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (vorname, nachname, geschlecht, adresse, alter) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patient.getVorname());
            stmt.setString(2, patient.getNachname());
            stmt.setString(3, patient.getGeschlecht());
            stmt.setString(4, patient.getAdresse());
            stmt.setInt(5, patient.getAlter());
            stmt.executeUpdate();
        }
    }

    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection connection = connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("id"),
                        rs.getString("vorname"),
                        rs.getString("nachname"),
                        rs.getString("geschlecht"),
                        rs.getString("adresse"),
                        rs.getInt("alter")
                ));
            }
        }
        return patients;
    }

    public void updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET vorname = ?, nachname = ?, geschlecht = ?, adresse = ?, alter = ? WHERE id = ?";
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patient.getVorname());
            stmt.setString(2, patient.getNachname());
            stmt.setString(3, patient.getGeschlecht());
            stmt.setString(4, patient.getAdresse());
            stmt.setInt(5, patient.getAlter());
            stmt.setInt(6, patient.getId());
            stmt.executeUpdate();
        }
    }

    public void deletePatient(int id) throws SQLException {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public boolean validatePatient(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM patients WHERE id = ?";
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}