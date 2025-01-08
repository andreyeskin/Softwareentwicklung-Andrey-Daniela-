package DAO;

import database.DatabaseConnection;
import models.Diagnose;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) für die Verwaltung von Diagnosen in der Tabelle "diagnosen".
 * Diese Klasse bietet CRUD-Methoden zur Interaktion mit der Datenbank.
 */
public class DiagnoseDAO {

    /**
     * Fügt eine neue Diagnose in die Tabelle "diagnosen" ein.
     *
     * @param diagnose die {@link Diagnose}, die hinzugefügt werden soll.
     * @throws SQLException wenn ein Fehler bei der Einfügung auftritt.
     */
    public void addDiagnose(Diagnose diagnose) throws SQLException {
        String sql = "INSERT INTO diagnosen (ICD10_Code, Name, Beschreibung) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, diagnose.getIcd10Code());
            stmt.setString(2, diagnose.getName());
            stmt.setString(3, diagnose.getBeschreibung());
            stmt.executeUpdate();
        }
    }

    /**
     * Ruft eine Diagnose anhand der Diagnose-ID aus der Tabelle "diagnosen" ab.
     *
     * @param diagnoseId die ID der gewünschten Diagnose.
     * @return die entsprechende {@link Diagnose} oder null, wenn keine gefunden wurde.
     * @throws SQLException wenn ein Fehler bei der Abfrage auftritt.
     */
    public Diagnose getDiagnoseById(int diagnoseId) throws SQLException {
        String sql = "SELECT * FROM diagnosen WHERE DiagnoseID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, diagnoseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapToDiagnose(rs);
                }
            }
        }
        return null;
    }

    /**
     * Ruft alle Diagnosen aus der Tabelle "diagnosen" ab.
     *
     * @return eine Liste von {@link Diagnose}-Objekten.
     * @throws SQLException wenn ein Fehler bei der Abfrage auftritt.
     */
    public List<Diagnose> getAllDiagnosen() throws SQLException {
        List<Diagnose> diagnosenList = new ArrayList<>();
        String sql = "SELECT DiagnoseID, ICD10_Code, Name, Beschreibung FROM diagnosen";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                diagnosenList.add(new Diagnose(
                        rs.getInt("DiagnoseID"),
                        rs.getString("ICD10_Code"),
                        rs.getString("Name"),
                        rs.getString("Beschreibung")
                ));
            }
        }
        return diagnosenList;
    }

    /**
     * Sucht Diagnosen anhand eines ICD-10-Codes.
     *
     * @param icd10Code der ICD-10-Code, nach dem gesucht werden soll.
     * @return eine Liste von {@link Diagnose}-Objekten, die den Suchkriterien entsprechen.
     * @throws SQLException wenn ein Fehler bei der Abfrage auftritt.
     */
    public List<Diagnose> searchDiagnosenByICD10(String icd10Code) throws SQLException {
        List<Diagnose> diagnoseList = new ArrayList<>();
        String sql = "SELECT * FROM diagnosen WHERE ICD10_Code LIKE ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + icd10Code + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    diagnoseList.add(mapToDiagnose(rs));
                }
            }
        }
        return diagnoseList;
    }

    /**
     * Aktualisiert eine vorhandene Diagnose in der Tabelle "diagnosen".
     *
     * @param diagnose die {@link Diagnose} mit den aktualisierten Werten.
     * @throws SQLException wenn ein Fehler bei der Aktualisierung auftritt.
     */
    public void updateDiagnose(Diagnose diagnose) throws SQLException {
        String sql = "UPDATE diagnosen SET ICD10_Code = ?, Name = ?, Beschreibung = ? WHERE DiagnoseID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, diagnose.getIcd10Code());
            stmt.setString(2, diagnose.getName());
            stmt.setString(3, diagnose.getBeschreibung());
            stmt.setInt(4, diagnose.getDiagnoseId());
            stmt.executeUpdate();
        }
    }

    /**
     * Ruft die Diagnose-ID anhand des Namens ab.
     *
     * @param diagnoseName der Name der Diagnose.
     * @return die Diagnose-ID oder -1, wenn keine Diagnose gefunden wurde.
     * @throws SQLException wenn ein Fehler bei der Abfrage auftritt.
     */
    public int getDiagnoseIdByName(String diagnoseName) throws SQLException {
        int diagnoseId = -1;
        String sql = "SELECT DiagnoseID FROM diagnosen WHERE Name = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, diagnoseName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    diagnoseId = resultSet.getInt("DiagnoseID");
                }
            }
        }
        return diagnoseId;
    }

    /**
     * Ruft die Diagnose-ID anhand des ICD-10-Codes ab.
     *
     * @param icd10Code der ICD-10-Code.
     * @return die Diagnose-ID oder -1, wenn keine Diagnose gefunden wurde.
     * @throws SQLException wenn ein Fehler bei der Abfrage auftritt.
     */
    public int getDiagnoseIdByICD10(String icd10Code) throws SQLException {
        int diagnoseId = -1;
        String sql = "SELECT DiagnoseID FROM diagnosen WHERE ICD10_Code = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, icd10Code);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    diagnoseId = resultSet.getInt("DiagnoseID");
                }
            }
        }
        return diagnoseId;
    }

    /**
     * Löscht eine Diagnose aus der Tabelle "diagnosen".
     *
     * @param diagnoseId die ID der Diagnose, die gelöscht werden soll.
     * @throws SQLException wenn ein Fehler beim Löschen auftritt.
     */
    public void deleteDiagnose(int diagnoseId) throws SQLException {
        String sql = "DELETE FROM diagnosen WHERE DiagnoseID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, diagnoseId);
            stmt.executeUpdate();
        }
    }

    /**
     * Hilfsmethode: Wandelt ein ResultSet in ein {@link Diagnose}-Objekt um.
     *
     * @param rs das ResultSet, das in ein Diagnose-Objekt umgewandelt werden soll.
     * @return ein {@link Diagnose}-Objekt.
     * @throws SQLException wenn ein Fehler bei der Umwandlung auftritt.
     */
    private Diagnose mapToDiagnose(ResultSet rs) throws SQLException {
        Diagnose diagnose = new Diagnose();
        diagnose.setDiagnoseId(rs.getInt("DiagnoseID"));
        diagnose.setIcd10Code(rs.getString("ICD10_Code"));
        diagnose.setName(rs.getString("Name"));
        diagnose.setBeschreibung(rs.getString("Beschreibung"));
        return diagnose;
    }
}
