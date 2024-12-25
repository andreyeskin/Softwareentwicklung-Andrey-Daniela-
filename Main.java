import Panels.MainFrame;
import database.DatabaseConnection;
import database.UserDatabaseConnection;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // FlatLaf Theme
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // LoginGUI erstellen
            LoginGUI loginGUI = new LoginGUI();
            UserDatabaseConnection userDb = new UserDatabaseConnection(); // Verbindung zur BenutzerDatenbank

            // Login-Aktion
            loginGUI.setLoginAction((username, password) -> {
                if (userDb.authenticate(username, password).isPresent()) {
                    // Login erfolgreich
                    loginGUI.close();
                    try {
                        new MainFrame().setVisible(true); // Haupt-GUI anzeigen
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Login fehlgeschlagen
                    loginGUI.showMessage("Ungültiger Benutzername oder Passwort!");
                }
            });

            loginGUI.show(); // Login-Fenster anzeigen
        });

        SwingUtilities.invokeLater(() -> {
            DatabaseConnection.testConnection(); // Testet die Verbindung bei Start
        });
    }
}