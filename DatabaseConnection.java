public class DatabaseConnection {

    package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    /**
     * Die Klasse DatabaseConnection stellt die Verbindung zur MySQL-Datenbank her.
     */
    public class DatabaseConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/patient_db";
        private static final String USER = "root";
        private static final String PASSWORD = "dein_passwort";

        /**
         * Erstellt eine Verbindung zur Datenbank.
         *
         * @return Verbindung zur Datenbank
         * @throws SQLException falls die Verbindung fehlschlägt
         */
        public static Connection connect() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }

        /**
         * Schließt die Verbindung zur Datenbank.
         *
         * @param connection die zu schließende Verbindung
         */
        public static void disconnect(Connection connection) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
