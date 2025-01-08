package Panels;

import DAO.MedikamentDAO;
import models.Medikament;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Das Panel MedikamentePanel ermöglicht die Verwaltung von Medikamenten.
 * Es bietet Funktionen zum Hinzufügen, Bearbeiten, Löschen und Anzeigen von Medikamenten in einer Tabelle.
 */
public class MedikamentePanel extends JPanel {
    private JTable medikamenteTable;
    private DefaultTableModel tableModel;

    private JTextField nameField;
    private JTextField beschreibungField;

    private MedikamentDAO medikamentDAO;

    /**
     * Konstruktor für das MedikamentePanel.
     * Initialisiert die Benutzeroberfläche und lädt die Medikamentendaten aus der Datenbank.
     *
     * @throws SQLException wenn ein Fehler bei der Datenbankverbindung auftritt.
     */
    public MedikamentePanel() throws SQLException {
        medikamentDAO = new MedikamentDAO();
        setLayout(new BorderLayout());

        // Tabelle für Medikamente
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Beschreibung"}, 0);
        medikamenteTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(medikamenteTable);
        add(scrollPane, BorderLayout.CENTER);

        // Listener für die Tabellenauswahl
        medikamenteTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = medikamenteTable.getSelectedRow();
                if (selectedRow != -1) {
                    String name = (String) tableModel.getValueAt(selectedRow, 1);
                    String beschreibung = (String) tableModel.getValueAt(selectedRow, 2);

                    nameField.setText(name);
                    beschreibungField.setText(beschreibung);
                }
            }
        });

        // Formular für Eingaben
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField();
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Beschreibung:"), gbc);

        gbc.gridx = 1;
        beschreibungField = new JTextField();
        formPanel.add(beschreibungField, gbc);

        // Buttons für Aktionen
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Hinzufügen");
        JButton editButton = new JButton("Bearbeiten");
        JButton deleteButton = new JButton("Löschen");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.SOUTH);

        // Event-Listener für Buttons
        addButton.addActionListener(e -> addMedikament());
        editButton.addActionListener(e -> updateMedikament());
        deleteButton.addActionListener(e -> deleteMedikament());

        // Medikamentendaten laden
        loadMedikamenteData();
    }

    /**
     * Lädt die Medikamentendaten aus der Datenbank und zeigt sie in der Tabelle an.
     */
    private void loadMedikamenteData() {
        try {
            tableModel.setRowCount(0); // Tabelle zurücksetzen
            List<Medikament> medikamente = medikamentDAO.getAllMedikamente();
            for (Medikament medikament : medikamente) {
                tableModel.addRow(new Object[]{
                        medikament.getMedikamentId(),
                        medikament.getName(),
                        medikament.getBeschreibung()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Laden der Medikamente: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Fügt ein neues Medikament hinzu.
     * Validiert die Eingabefelder und speichert das Medikament in der Datenbank.
     */
    private void addMedikament() {
        String name = nameField.getText();
        String beschreibung = beschreibungField.getText();

        if (name.isEmpty() || beschreibung.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Alle Felder müssen ausgefüllt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Medikament medikament = new Medikament(0, name, beschreibung);
            medikamentDAO.addMedikament(medikament);
            JOptionPane.showMessageDialog(this, "Medikament hinzugefügt!");
            loadMedikamenteData();
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Hinzufügen: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Aktualisiert ein bestehendes Medikament.
     * Validiert die Eingabefelder und speichert die Änderungen in der Datenbank.
     */
    private void updateMedikament() {
        int selectedRow = medikamenteTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie ein Medikament aus!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int medikamentId = (int) tableModel.getValueAt(selectedRow, 0);
            String name = nameField.getText();
            String beschreibung = beschreibungField.getText();

            if (name.isEmpty() || beschreibung.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Alle Felder müssen ausgefüllt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Medikament medikament = new Medikament(medikamentId, name, beschreibung);
            medikamentDAO.updateMedikament(medikament);
            JOptionPane.showMessageDialog(this, "Medikament aktualisiert!");
            loadMedikamenteData();
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Bearbeiten: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Löscht ein bestehendes Medikament.
     */
    private void deleteMedikament() {
        int selectedRow = medikamenteTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie ein Medikament aus!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int medikamentId = (int) tableModel.getValueAt(selectedRow, 0);
            medikamentDAO.deleteMedikament(medikamentId);
            JOptionPane.showMessageDialog(this, "Medikament gelöscht!");
            loadMedikamenteData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Löschen: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Leert die Eingabefelder.
     */
    private void clearFields() {
        nameField.setText("");
        beschreibungField.setText("");
    }
}
