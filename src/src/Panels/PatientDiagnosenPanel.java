package Panels;

import DAO.DiagnoseDAO;
import DAO.PatientDAO;
import DAO.PatientDiagnosenDAO;
import models.Diagnose;
import models.Patient;
import models.PatientDiagnose;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class PatientDiagnosenPanel extends JPanel {
    private JTable patientDiagnosenTable;
    private DefaultTableModel tableModel;

    private JComboBox<String> patientComboBox;
    private JComboBox<String> diagnoseComboBox;
    private JTextField bemerkungField;
    private JTextField datumField;

    private JButton zuordnenButton;
    private JButton loeschenButton;
    private JButton aktualisierenButton;

    private PatientDiagnosenDAO patientDiagnosenDAO;
    private PatientDAO patientDAO;
    private DiagnoseDAO diagnoseDAO;

    public PatientDiagnosenPanel() {
        patientDiagnosenDAO = new PatientDiagnosenDAO();
        patientDAO = new PatientDAO();
        diagnoseDAO = new DiagnoseDAO();

        setLayout(new BorderLayout());

        // Tabelle für Patient-Diagnosen
        tableModel = new DefaultTableModel(new String[]{
                "PatientDiagnoseID", "Patient", "Diagnose", "ICD-10", "Bemerkung", "Datum"
        }, 0);
        patientDiagnosenTable = new JTable(tableModel);

        // Formular für Eingaben
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Abstände zwischen Komponenten
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Patient
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Patient:"), gbc);

        gbc.gridx = 1;
        patientComboBox = new JComboBox<>();
        formPanel.add(patientComboBox, gbc);

        // Diagnose
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Diagnose:"), gbc);

        gbc.gridx = 1;
        diagnoseComboBox = new JComboBox<>();
        formPanel.add(diagnoseComboBox, gbc);

        // Bemerkung
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Bemerkung:"), gbc);

        gbc.gridx = 1;
        bemerkungField = new JTextField(20);
        formPanel.add(bemerkungField, gbc);

        // Datum
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Datum (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        datumField = new JTextField(20);
        formPanel.add(datumField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        zuordnenButton = new JButton("Zuordnen");
        loeschenButton = new JButton("Löschen");
        aktualisierenButton = new JButton("Aktualisieren");
        buttonPanel.add(zuordnenButton);
        buttonPanel.add(loeschenButton);
        buttonPanel.add(aktualisierenButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.SOUTH);

        // Events
        zuordnenButton.addActionListener(e -> addPatientDiagnose());
        loeschenButton.addActionListener(e -> deletePatientDiagnose());
        aktualisierenButton.addActionListener(e -> updatePatientDiagnose());



        // Tabelle auswählen

        // Tabelle für Patient-Diagnosen

        tableModel = new DefaultTableModel(new String[]{
                "PatientDiagnoseID", "Patient", "Diagnose", "ICD-10", "Bemerkung", "Datum"
        }, 0);
        patientDiagnosenTable = new JTable(tableModel);

        // PatientDiagnoseID-Spalte unsichtbar machen
        patientDiagnosenTable.getColumnModel().removeColumn(patientDiagnosenTable.getColumnModel().getColumn(0));

// Tabelle in ScrollPane einfügen
        JScrollPane scrollPane = new JScrollPane(patientDiagnosenTable);

// ScrollPane in das Panel einfügen
        add(scrollPane, BorderLayout.CENTER);

        patientDiagnosenTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && patientDiagnosenTable.getSelectedRow() != -1) {
                int selectedRow = patientDiagnosenTable.getSelectedRow();

                // Aktualisiere Patient Dropdown
                String patientInfo = patientDiagnosenTable.getValueAt(selectedRow, 0).toString(); // Spalte 1: Patient
                for (int i = 0; i < patientComboBox.getItemCount(); i++) {
                    if (patientComboBox.getItemAt(i).equals(patientInfo)) {
                        patientComboBox.setSelectedIndex(i);
                        break;
                    }
                }

                // Aktualisiere Diagnose Dropdown
                String diagnoseInfo = patientDiagnosenTable.getValueAt(selectedRow, 1).toString(); // Spalte 2: Diagnose
                for (int i = 0; i < diagnoseComboBox.getItemCount(); i++) {
                    if (diagnoseComboBox.getItemAt(i).startsWith(diagnoseInfo)) {
                        diagnoseComboBox.setSelectedIndex(i);
                        break;
                    }


                }



                // Aktualisiere Bemerkung und Datum
                bemerkungField.setText(patientDiagnosenTable.getValueAt(selectedRow, 3).toString()); // Spalte 4: Bemerkung
                datumField.setText(patientDiagnosenTable.getValueAt(selectedRow, 4).toString());    // Spalte 5: Datum
            }
        });




        // Daten laden
        loadComboBoxData();
        loadPatientDiagnosenData();
    }

    public void updatePatientenDropdown() {
        patientComboBox.removeAllItems(); // Vorherige Einträge entfernen
        try {
            List<Patient> patients = patientDAO.getAllPatients(); // Patientenliste abrufen
            for (Patient patient : patients) {
                patientComboBox.addItem(patient.getVorname() + " " + patient.getNachname() + " (ID: " + patient.getId() + ")");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Aktualisieren der Patientenliste: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    void loadComboBoxData() {
        try {
            // Patienten in ComboBox laden
            List<Patient> patients = patientDAO.getAllPatients();
            patientComboBox.removeAllItems();
            for (Patient patient : patients) {
                patientComboBox.addItem(patient.getVorname() + " " + patient.getNachname() + " (ID: " + patient.getId() + ")");
            }



            // Diagnosen in ComboBox laden
            List<Diagnose> diagnosen = diagnoseDAO.getAllDiagnosen();
            diagnoseComboBox.removeAllItems();
            for (Diagnose diagnose : diagnosen) {
                diagnoseComboBox.addItem(diagnose.getName() + " (ICD-10: " + diagnose.getIcd10Code() + ")");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Laden der Listen: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadPatientDiagnosenData() {
        try {
            tableModel.setRowCount(0);
            List<String[]> patientDiagnosen = patientDiagnosenDAO.getDetailedPatientDiagnosen();
            for (String[] row : patientDiagnosen) {
                System.out.println("Geladene Zeile: " + Arrays.toString(row)); // Debugging
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Laden der Patient-Diagnosen: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }




    private void addPatientDiagnose() {
        try {
            String patientInfo = (String) patientComboBox.getSelectedItem();
            int patientId = Integer.parseInt(patientInfo.substring(patientInfo.indexOf("ID: ") + 4, patientInfo.indexOf(")")));

            String diagnoseInfo = (String) diagnoseComboBox.getSelectedItem();
            int diagnoseId = diagnoseDAO.getDiagnoseIdByName(diagnoseInfo.substring(0, diagnoseInfo.indexOf(" (ICD-10:")));

            String bemerkung = bemerkungField.getText();
            String datum = datumField.getText();

            if (bemerkung.isEmpty() || datum.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Alle Felder müssen ausgefüllt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PatientDiagnose patientDiagnose = new PatientDiagnose(0, patientId, diagnoseId, bemerkung, datum);
            patientDiagnosenDAO.addPatientDiagnose(patientDiagnose);
            JOptionPane.showMessageDialog(this, "Diagnose zugeordnet!");
            loadPatientDiagnosenData();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Hinzufügen: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePatientDiagnose() {
        int selectedRow = patientDiagnosenTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Eintrag aus!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String patientInfo = (String) patientComboBox.getSelectedItem();
            int patientId = Integer.parseInt(patientInfo.substring(patientInfo.indexOf("ID: ") + 4, patientInfo.indexOf(")")));

            String diagnoseInfo = (String) diagnoseComboBox.getSelectedItem();
            int diagnoseId = diagnoseDAO.getDiagnoseIdByName(diagnoseInfo.substring(0, diagnoseInfo.indexOf(" (ICD-10:")));

            String bemerkung = bemerkungField.getText();
            String datum = datumField.getText();

            // PatientDiagnoseID aus dem Modell holen, unabhängig von der Sichtbarkeit der Spalte
            int id = Integer.parseInt(patientDiagnosenTable.getModel().getValueAt(selectedRow, 0).toString());


            PatientDiagnose patientDiagnose = new PatientDiagnose(id, patientId, diagnoseId, bemerkung, datum);
            patientDiagnosenDAO.updatePatientDiagnose(patientDiagnose);
            JOptionPane.showMessageDialog(this, "Eintrag aktualisiert!");
            loadPatientDiagnosenData();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Aktualisieren: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePatientDiagnose() {
        int selectedRow = patientDiagnosenTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Eintrag aus!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // ID aus dem Modell holen, nicht direkt aus der Tabelle
            int id = Integer.parseInt(patientDiagnosenTable.getModel().getValueAt(selectedRow, 0).toString());
            patientDiagnosenDAO.deletePatientDiagnose(id);
            JOptionPane.showMessageDialog(this, "Eintrag gelöscht!");
            loadPatientDiagnosenData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Löschen: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void clearFields() {
        bemerkungField.setText("");
        datumField.setText("");
    }
}