package Panels;

import DAO.DiagnoseDAO;
import models.Diagnose;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Das Panel DiagnosenPanel ermöglicht die Verwaltung von Diagnosen.
 * Es bietet Funktionen zum Hinzufügen, Bearbeiten, Löschen und Anzeigen von Diagnosen in einer Tabelle.
 */
public class DiagnosenPanel extends JPanel {
    private JTable diagnosenTable;
    private DefaultTableModel tableModel;

    private JTextField icd10Field;
    private JTextField nameField;
    private JTextField beschreibungField;

    private DiagnoseDAO diagnoseDAO;

    private PatientDiagnosenPanel patientDiagnosenPanel;

    /**
     * Verknüpft dieses Panel mit einem PatientDiagnosenPanel, um die Daten direkt zu aktualisieren.
     *
     * @param panel Das PatientDiagnosenPanel, das aktualisiert werden soll.
     */
    public void setPatientDiagnosenPanel(PatientDiagnosenPanel panel) {
        this.patientDiagnosenPanel = panel;
    }

    /**
     * Konstruktor des DiagnosenPanels.
     * Initialisiert die GUI-Komponenten, die Tabelle und lädt die Diagnosedaten.
     */
    public DiagnosenPanel() {
        diagnoseDAO = new DiagnoseDAO();
        setLayout(new BorderLayout());

        // Tabelle für Diagnosen
        tableModel = new DefaultTableModel(new String[]{"ID", "ICD-10", "Name", "Beschreibung"}, 0);
        diagnosenTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(diagnosenTable);
        add(scrollPane, BorderLayout.CENTER);

        // Listener für die Auswahl in der Tabelle
        diagnosenTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = diagnosenTable.getSelectedRow();
                if (selectedRow != -1) {
                    String icd10 = (String) tableModel.getValueAt(selectedRow, 1);
                    String name = (String) tableModel.getValueAt(selectedRow, 2);
                    String beschreibung = (String) tableModel.getValueAt(selectedRow, 3);

                    icd10Field.setText(icd10);
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

        // Label und Eingabefelder hinzufügen
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("ICD-10 Code:"), gbc);
        gbc.gridx = 1;
        icd10Field = new JTextField();
        formPanel.add(icd10Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField();
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
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
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
        add(formPanel, BorderLayout.SOUTH);

        // Listener für Buttons
        addButton.addActionListener(e -> addDiagnose());
        editButton.addActionListener(e -> updateDiagnose());
        deleteButton.addActionListener(e -> deleteDiagnose());

        // Diagnosedaten laden
        loadDiagnosenData();
    }

    /**
     * Lädt die Diagnosedaten aus der Datenbank und zeigt sie in der Tabelle an.
     */
    private void loadDiagnosenData() {
        try {
            tableModel.setRowCount(0); // Tabelle zurücksetzen
            List<Diagnose> diagnosen = diagnoseDAO.getAllDiagnosen();
            for (Diagnose diagnose : diagnosen) {
                tableModel.addRow(new Object[]{
                        diagnose.getDiagnoseId(),
                        diagnose.getIcd10Code(),
                        diagnose.getName(),
                        diagnose.getBeschreibung()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Laden der Diagnosen: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Fügt eine neue Diagnose hinzu.
     * Validiert die Eingabefelder und speichert die Diagnose in der Datenbank.
     */
    private void addDiagnose() {
        String icd10 = icd10Field.getText();
        String name = nameField.getText();
        String beschreibung = beschreibungField.getText();

        if (icd10.isEmpty() || name.isEmpty() || beschreibung.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Alle Felder müssen ausgefüllt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Diagnose diagnose = new Diagnose(0, icd10, name, beschreibung);
            diagnoseDAO.addDiagnose(diagnose);
            JOptionPane.showMessageDialog(this, "Diagnose hinzugefügt!");

            if (patientDiagnosenPanel != null) {
                patientDiagnosenPanel.loadComboBoxData();
            }

            loadDiagnosenData();
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Hinzufügen: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Aktualisiert die ausgewählte Diagnose.
     * Validiert die Eingabefelder und speichert die Änderungen in der Datenbank.
     */
    private void updateDiagnose() {
        int selectedRow = diagnosenTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Diagnose aus!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int diagnoseId = (int) tableModel.getValueAt(selectedRow, 0);
            String icd10 = icd10Field.getText();
            String name = nameField.getText();
            String beschreibung = beschreibungField.getText();

            if (icd10.isEmpty() || name.isEmpty() || beschreibung.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Alle Felder müssen ausgefüllt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Diagnose diagnose = new Diagnose(diagnoseId, icd10, name, beschreibung);
            diagnoseDAO.updateDiagnose(diagnose);
            JOptionPane.showMessageDialog(this, "Diagnose aktualisiert!");
            loadDiagnosenData();
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Bearbeiten: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Löscht die ausgewählte Diagnose.
     */
    private void deleteDiagnose() {
        int selectedRow = diagnosenTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Diagnose aus!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int diagnoseId = (int) tableModel.getValueAt(selectedRow, 0);
            diagnoseDAO.deleteDiagnose(diagnoseId);
            JOptionPane.showMessageDialog(this, "Diagnose gelöscht!");
            loadDiagnosenData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Löschen: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Leert die Eingabefelder.
     */
    private void clearFields() {
        icd10Field.setText("");
        nameField.setText("");
        beschreibungField.setText("");
    }
}
