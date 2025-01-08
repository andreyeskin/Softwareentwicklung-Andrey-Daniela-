package DAO;

import database.DatabaseConnection;
import models.PatientDiagnose;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) für die Verwaltung von Patientendiagnosen in der Tabelle "patient_diagnosen".
 * Diese Klasse bietet CRUD-Methoden sowie Abfragemethoden für detaillierte Diagnoseninformationen.
 */
public class PatientDiagnosenDAO {

    /**
     * Ruft alle Patientendiagnosen aus der Tabelle "patient_diagnosen" ab.
     *
     * @return eine Liste von {@link PatientDiagnose}-Objekten.
     * @throws SQLException wenn ein Fehler bei der Datenbankabfrage auftritt.
     */
    public List<PatientDiagnose> getAllPatientDiagnosen() throws SQLException {
        List<PatientDiagnose> patientDiagnosenList = new ArrayList<>();
        String sql = """
        SELECT pd.PatientDiagnoseID, pd.PatientID, pd.DiagnoseID, pd.Datum, pd.Bemerkung, 
               d.Name AS DiagnoseName, d.Beschreibung AS DiagnoseBeschreibung, 
               CONCAT(p.vorname, ' ', p.nachname) AS PatientName
        FROM patient_diagnosen pd
        LEFT JOIN diagnosen d ON pd.DiagnoseID = d.DiagnoseID
        LEFT JOIN patients p ON pd.PatientID = p.id
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PatientDiagnose patientDiagnose = new PatientDiagnose();
                patientDiagnose.setPatientDiagnoseId(rs.getInt("PatientDiagnoseID"));
                patientDiagnose.setPatientId(rs.getInt("PatientID"));
                patientDiagnose.setDiagnoseId(rs.getInt("DiagnoseID"));
                patientDiagnose.setDatum(rs.getString("Datum"));
                patientDiagnose.setBemerkung(rs.getString("Bemerkung"));
                patientDiagnose.setDiagnoseName(rs.getString("DiagnoseName"));
                patientDiagnose.setDiagnoseBeschreibung(rs.getString("DiagnoseBeschreibung"));
                patientDiagnose.setPatientName(rs.getString("PatientName"));
                patientDiagnosenList.add(patientDiagnose);
            }
        }
        return patientDiagnosenList;
    }

    /**
     * Fügt eine neue Patientendiagnose in die Tabelle "patient_diagnosen" ein.
     *
     * @param patientDiagnose das {@link PatientDiagnose}-Objekt, das hinzugefügt werden soll.
     * @throws SQLException wenn ein Fehler bei der Einfügung auftritt.
     */
    public void addPatientDiagnose(PatientDiagnose patientDiagnose) throws SQLException {
        String sql = "INSERT INTO patient_diagnosen (PatientID, DiagnoseID, Datum, Bemerkung) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, patientDiagnose.getPatientId());
            stmt.setInt(2, patientDiagnose.getDiagnoseId());
            stmt.setString(3, patientDiagnose.getDatum());
            stmt.setString(4, patientDiagnose.getBemerkung());
            stmt.executeUpdate();
        }
    }

    /**
     * Aktualisiert eine vorhandene Patientendiagnose in der Tabelle "patient_diagnosen".
     *
     * @param patientDiagnose das {@link PatientDiagnose}-Objekt mit den aktualisierten Werten.
     * @throws SQLException wenn ein Fehler bei der Aktualisierung auftritt.
     */
    public void updatePatientDiagnose(PatientDiagnose patientDiagnose) throws SQLException {
        String sql = "UPDATE patient_diagnosen SET PatientID = ?, DiagnoseID = ?, Datum = ?, Bemerkung = ? WHERE PatientDiagnoseID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, patientDiagnose.getPatientId());
            stmt.setInt(2, patientDiagnose.getDiagnoseId());
            stmt.setString(3, patientDiagnose.getDatum());
            stmt.setString(4, patientDiagnose.getBemerkung());
            stmt.setInt(5, patientDiagnose.getPatientDiagnoseId());
            stmt.executeUpdate();
        }
    }

    /**
     * Löscht eine Patientendiagnose aus der Tabelle "patient_diagnosen".
     *
     * @param patientDiagnoseId die ID der Patientendiagnose, die gelöscht werden soll.
     * @throws SQLException wenn ein Fehler bei der Löschung auftritt.
     */
    public void deletePatientDiagnose(int patientDiagnoseId) throws SQLException {
        String sql = "DELETE FROM patient_diagnosen WHERE PatientDiagnoseID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, patientDiagnoseId);
            stmt.executeUpdate();
        }
    }

    /**
     * Ruft detaillierte Informationen zu allen Patientendiagnosen ab, einschließlich Patientennamen, Diagnosenamen und ICD10-Codes.
     *
     * @return eine Liste von Arrays, wobei jedes Array die Informationen einer Patientendiagnose enthält.
     * @throws SQLException wenn ein Fehler bei der Abfrage auftritt.
     */
    public List<String[]> getDetailedPatientDiagnosen() throws SQLException {
        List<String[]> result = new ArrayList<>();
        String sql = """
        SELECT 
            pd.PatientDiagnoseID,
            CONCAT(p.vorname, ' ', p.nachname, ' (ID: ', p.id, ')') AS Patient,
            d.name AS Diagnose,
            d.icd10_code AS ICD10,
            pd.bemerkung,
            pd.datum
        FROM 
            patient_diagnosen pd
        JOIN 
            patients p ON pd.PatientID = p.id
        JOIN 
            diagnosen d ON pd.DiagnoseID = d.DiagnoseID;
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] row = {
                        String.valueOf(rs.getInt("PatientDiagnoseID")),
                        rs.getString("Patient"),    // Vor- und Nachname mit ID
                        rs.getString("Diagnose"),  // Diagnose-Name
                        rs.getString("ICD10"),     // ICD-10-Code
                        rs.getString("bemerkung"), // Bemerkung
                        rs.getString("datum")      // Datum
                };
                result.add(row);
            }
        }
        return result;
    }
}
