package database;

import java.sql.*;
import java.util.Optional;

/**
 * Die Klasse UserDatabaseConnection bietet Methoden für den Zugriff auf die BenutzerDatenbank.
 * Sie ermöglicht die Benutzer-Authentifizierung, das Hinzufügen neuer Benutzer und das Abrufen von Benutzerinformationen.
 */
public class UserDatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/BenutzerDatenbank";
    private static final String USER = "root";
    private static final String PASSWORD = "daniyfelI123";

    /**
     * Stellt eine Verbindung zur BenutzerDatenbank her.
     *
     * @return eine {@link Connection} zur BenutzerDatenbank.
     * @throws SQLException wenn ein Fehler bei der Verbindung zur Datenbank auftritt.
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Überprüft die Benutzeranmeldung anhand von Benutzername und Passwort.
     *
     * @param username der Benutzername des Benutzers.
     * @param password das Passwort des Benutzers.
     * @return ein {@link Optional} mit dem Benutzernamen, wenn die Authentifizierung erfolgreich ist,
     *         andernfalls ein leeres {@link Optional}.
     */
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

    /**
     * Fügt einen neuen Benutzer in die Datenbank ein.
     *
     * @param username der Benutzername des neuen Benutzers.
     * @param password das Passwort des neuen Benutzers.
     * @throws SQLException wenn ein Fehler bei der Einfügung auftritt.
     */
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

    /**
     * Gibt eine Liste aller Benutzer aus der Tabelle "Benutzer" aus.
     * Jeder Benutzer wird mit seiner ID und seinem Benutzernamen ausgegeben.
     */
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
