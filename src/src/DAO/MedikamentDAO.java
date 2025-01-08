package DAO;

import database.DatabaseConnection;
import models.Medikament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) für die Verwaltung von CRUD-Operationen der Tabelle "medikamente".
 * Diese Klasse interagiert mit der Datenbank, um Abfragen zu Medikamenten durchzuführen.
 */
public class MedikamentDAO {
    private Connection connection;

    /**
     * Konstruktor von MedikamentDAO.
     * Stellt die Verbindung zur Datenbank her.
     *
     * @throws SQLException wenn ein Fehler bei der Verbindung zur Datenbank auftritt.
     */
    public MedikamentDAO() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    /**
     * Ruft alle Medikamente aus der Tabelle "medikamente" ab.
     *
     * @return eine Liste von {@link Medikament}-Objekten, die die Medikamente repräsentieren.
     * @throws SQLException wenn ein Fehler bei der Abfrage auftritt.
     */
    public List<Medikament> getAllMedikamente() throws SQLException {
        List<Medikament> medikamente = new ArrayList<>();
        String query = "SELECT * FROM medikamente";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                medikamente.add(new Medikament(
                        rs.getInt("MedikamentID"),
                        rs.getString("Name"),
                        rs.getString("Beschreibung")
                ));
            }
        }
        return medikamente;
    }

    /**
     * Fügt ein neues Medikament in die Tabelle "medikamente" ein.
     *
     * @param medikament das {@link Medikament}-Objekt, das die Informationen des neuen Medikaments enthält.
     * @throws SQLException wenn ein Fehler beim Einfügen auftritt.
     */
    public void addMedikament(Medikament medikament) throws SQLException {
        String query = "INSERT INTO medikamente (Name, Beschreibung) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, medikament.getName());
            pstmt.setString(2, medikament.getBeschreibung());
            pstmt.executeUpdate();
        }
    }

    /**
     * Aktualisiert ein vorhandenes Medikament in der Tabelle "medikamente".
     *
     * @param medikament das {@link Medikament}-Objekt, das die neuen Daten des Medikaments enthält.
     * @throws SQLException wenn ein Fehler bei der Aktualisierung auftritt.
     */
    public void updateMedikament(Medikament medikament) throws SQLException {
        String query = "UPDATE medikamente SET Name = ?, Beschreibung = ? WHERE MedikamentID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, medikament.getName());
            pstmt.setString(2, medikament.getBeschreibung());
            pstmt.setInt(3, medikament.getMedikamentId());
            pstmt.executeUpdate();
        }
    }

    /**
     * Löscht ein Medikament aus der Tabelle "medikamente".
     *
     * @param medikamentId die ID des Medikaments, das gelöscht werden soll.
     * @throws SQLException wenn ein Fehler beim Löschen auftritt.
     */
    public void deleteMedikament(int medikamentId) throws SQLException {
        String query = "DELETE FROM medikamente WHERE MedikamentID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, medikamentId);
            pstmt.executeUpdate();
        }
    }
}
