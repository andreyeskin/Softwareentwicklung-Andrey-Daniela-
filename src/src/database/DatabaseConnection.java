package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Diese Klasse bietet eine Singleton-Verbindung zur MySQL-Datenbank.
 * Sie enthält Methoden zum Abrufen, Testen und Schließen der Datenbankverbindung.
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/patient_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password123";

    private static Connection connection;

    /**
     * Gibt eine Verbindung zur Datenbank zurück.
     * Wenn keine Verbindung besteht oder die Verbindung geschlossen ist, wird eine neue Verbindung hergestellt.
     *
     * @return eine {@link Connection} zur Datenbank.
     * @throws SQLException wenn ein Fehler bei der Verbindung zur Datenbank auftritt.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    /**
     * Testet die Datenbankverbindung, indem versucht wird, eine Verbindung herzustellen und ihren Status zu überprüfen.
     * Gibt eine Erfolgsmeldung aus, wenn die Verbindung hergestellt werden konnte, oder einen Fehler, wenn nicht.
     */
    public static void testConnection() {
        try (Connection connection = getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Datenbankverbindung erfolgreich!");
            }
        } catch (SQLException e) {
            System.err.println("Fehler bei der Verbindung: " + e.getMessage());
        }
    }

    /**
     * Schließt die bestehende Verbindung zur Datenbank, falls diese offen ist.
     * Gibt eine Meldung aus, wenn die Verbindung erfolgreich geschlossen wurde, oder einen Fehler, wenn das Schließen fehlschlägt.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Datenbankverbindung geschlossen.");
            } catch (SQLException e) {
                System.err.println("Fehler beim Schließen der Verbindung: " + e.getMessage());
            }
        }
    }
}
