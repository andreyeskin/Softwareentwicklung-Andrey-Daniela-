package DAO;

import database.DatabaseConnection;
import models.Medikament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedikamentDAO {
    private Connection connection;

    public MedikamentDAO() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

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

    public void addMedikament(Medikament medikament) throws SQLException {
        String query = "INSERT INTO medikamente (Name, Beschreibung) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, medikament.getName());
            pstmt.setString(2, medikament.getBeschreibung());
            pstmt.executeUpdate();
        }
    }

    public void updateMedikament(Medikament medikament) throws SQLException {
        String query = "UPDATE medikamente SET Name = ?, Beschreibung = ? WHERE MedikamentID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, medikament.getName());
            pstmt.setString(2, medikament.getBeschreibung());
            pstmt.setInt(3, medikament.getMedikamentId());
            pstmt.executeUpdate();
        }
    }

    public void deleteMedikament(int medikamentId) throws SQLException {
        String query = "DELETE FROM medikamente WHERE MedikamentID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, medikamentId);
            pstmt.executeUpdate();
        }
    }
}
