import com.formdev.flatlaf.FlatLightLaf;
import database.DatabaseConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        // FlatLaf aktivieren
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Failed to initialize FlatLaf.");
            e.printStackTrace();
        }

        // Anwendung starten
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Patientenverwaltung");
            GUI gui = new GUI(); // GUI erstellen

            // Aktion für das Speichern von Patienten
            gui.setSaveAction((name, age) -> {
                try (Connection connection = DatabaseConnection.connect()) {
                    String sql = "INSERT INTO patients (name, alter, adresse) VALUES (?, ?, ?)";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.setString(1, name);
                    stmt.setInt(2, Integer.parseInt(age));
                    stmt.setString(3, "Unbekannte Adresse");
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Patient gespeichert!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Fehler beim Speichern des Patienten.");
                }
            });

            // Aktion für das Anzeigen von Patienten
            gui.setShowAction(new Consumer<Void>() {
                @Override
                public void accept(Void unused) {
                    try (Connection connection = DatabaseConnection.connect()) {
                        String sql = "SELECT * FROM patients";
                        Statement stmt = connection.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);
                        StringBuilder result = new StringBuilder("Patientenliste:\n");
                        while (rs.next()) {
                            result.append("ID: ").append(rs.getInt("id"))
                                    .append(", Name: ").append(rs.getString("name"))
                                    .append(", Alter: ").append(rs.getInt("alter"))
                                    .append(", Adresse: ").append(rs.getString("adresse"))
                                    .append("\n");
                        }
                        JOptionPane.showMessageDialog(null, result.toString());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Fehler beim Abrufen der Patienten.");
                    }
                }
            });

            frame.setContentPane(gui.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
