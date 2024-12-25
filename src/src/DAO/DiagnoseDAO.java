package DAO;

import database.DatabaseConnection;
import models.Diagnose;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnoseDAO {

    // Diagnose hinzufügen
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

    // Diagnose abrufen (nach ID)
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

    // Alle Diagnosen abrufen
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

    // Diagnosen nach ICD-10-Code suchen
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

    // Diagnose aktualisieren
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


    // Diagnose löschen
    public void deleteDiagnose(int diagnoseId) throws SQLException {
        String sql = "DELETE FROM diagnosen WHERE DiagnoseID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, diagnoseId);
            stmt.executeUpdate();
        }
    }

    // Hilfsmethode: ResultSet in Diagnose-Objekt umwandeln
    private Diagnose mapToDiagnose(ResultSet rs) throws SQLException {
        Diagnose diagnose = new Diagnose();
        diagnose.setDiagnoseId(rs.getInt("DiagnoseID"));
        diagnose.setIcd10Code(rs.getString("ICD10_Code"));
        diagnose.setName(rs.getString("Name"));
        diagnose.setBeschreibung(rs.getString("Beschreibung"));
        return diagnose;
    }
}