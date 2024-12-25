package database;

import java.sql.*;
import java.util.Optional;

/**
 * Die Klasse UserDatabaseConnection bietet Methoden für den Zugriff auf die BenutzerDatenbank.
 */
public class UserDatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/BenutzerDatenbank";
    private static final String USER = "root";
    private static final String PASSWORD = "password123";

    // Methode zum Herstellen einer Verbindung zur BenutzerDatenbank
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Methode zur Überprüfung der Benutzeranmeldung
    public Optional<String> authenticate(String username, String password) {
        String sql = "SELECT benutzername FROM Benutzer WHERE benutzername = ? AND passwort = ?";
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getString("benutzername")); // Benutzer gefunden
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); // Kein Benutzer gefunden
    }

    // Methode zum Hinzufügen eines neuen Benutzers
    public void addUser(String username, String password) throws SQLException {
        String sql = "INSERT INTO Benutzer (benutzername, passwort) VALUES (?, ?)";
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            System.out.println("Benutzer erfolgreich hinzugefügt: " + username);
        }
    }

    // Methode zum Abrufen aller Benutzer
    public void listAllUsers() {
        String sql = "SELECT benutzer_id, benutzername FROM Benutzer";
        try (Connection connection = connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("benutzer_id") + ", Benutzername: " + rs.getString("benutzername"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
