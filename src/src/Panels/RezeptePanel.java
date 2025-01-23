package Panels;

import DAO.RezeptDAO;
import models.Rezept;
import models.Patient;
import models.Medikament;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class RezeptePanel extends JPanel {
    private JTable rezepteTable;
    private DefaultTableModel tableModel;

    private JComboBox<Patient> patientComboBox;
    private JComboBox<Medikament> medikamentComboBox;

    private JTextField dosierungField;
    private JTextField startDatumField;
    private JTextField endDatumField;
    private JTextField bemerkungField;

    private RezeptDAO rezeptDAO;

    private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }





    public RezeptePanel() throws SQLException {
        rezeptDAO = new RezeptDAO();
        setLayout(new BorderLayout());

        // Initialisierung der Komponenten
        patientComboBox = new JComboBox<>();
        medikamentComboBox = new JComboBox<>();
        dosierungField = new JTextField();
        startDatumField = new JTextField();
        endDatumField = new JTextField();
        bemerkungField = new JTextField();

        // Festlegen einer festen Größe für Textfelder und JComboBox.
        Dimension fieldSize = new Dimension(200, 25);
        patientComboBox.setPreferredSize(fieldSize);
        medikamentComboBox.setPreferredSize(fieldSize);
        dosierungField.setPreferredSize(fieldSize);
        startDatumField.setPreferredSize(fieldSize);
        endDatumField.setPreferredSize(fieldSize);
        bemerkungField.setPreferredSize(fieldSize);



        // Rezepttabelle
        tableModel = new DefaultTableModel(new String[]{"RezeptID", "Patient", "Medikament", "Dosierung", "Startdatum", "Enddatum", "Bemerkung"}, 0);
        rezepteTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(rezepteTable);
        add(scrollPane, BorderLayout.CENTER);







        rezepteTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Stellen wir sicher, dass das Ereignis abgeschlossen ist.
                fillFormFromTable(); // Methode zum Ausfüllen des Formulars
            }
        });




// Formular mit zwei Spalten
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

// Die erste Spalte
        gbc.gridx = 0; // Столбец 0
        gbc.gridy = 0;

        formPanel.add(new JLabel("Patient:"), gbc);

        gbc.gridy = 1;
        formPanel.add(new JLabel("Dosierung:"), gbc);

        gbc.gridy = 2;
        formPanel.add(new JLabel("Enddatum (YYYY-MM-DD):"), gbc);

// Felder für die erste Spalte
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(patientComboBox, gbc);

        gbc.gridy = 1;
        formPanel.add(dosierungField, gbc);

        gbc.gridy = 2;
        formPanel.add(endDatumField, gbc);

// Die zweite Spalte
        gbc.gridx = 2;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Medikament:"), gbc);

        gbc.gridy = 1;
        formPanel.add(new JLabel("Startdatum (YYYY-MM-DD):"), gbc);

        gbc.gridy = 2;
        formPanel.add(new JLabel("Bemerkung:"), gbc);


        gbc.gridx = 3;
        gbc.gridy = 0;
        formPanel.add(medikamentComboBox, gbc);

        gbc.gridy = 1;
        formPanel.add(startDatumField, gbc);

        gbc.gridy = 2;
        formPanel.add(bemerkungField, gbc);

// Hinzufügen von Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // Laden von Icons
       // ImageIcon addIcon = scaleIcon(new ImageIcon(getClass().getResource("/Lib/icons8-add-48.png")), 24, 24);
       // ImageIcon editIcon = scaleIcon(new ImageIcon(getClass().getResource("/Lib/icons8-edit-40.png")), 24, 24);
      //  ImageIcon deleteIcon = scaleIcon(new ImageIcon(getClass().getResource("/Lib/icons8-delete-48.png")), 24, 24);

        JButton addButton = new JButton("Hinzufügen" /*, addIcon*/);
        JButton editButton = new JButton("Bearbeiten"/*, editIcon*/);
        JButton deleteButton = new JButton("Löschen" /*,deleteIcon*/);
        JButton clearButton = new JButton("Formular leeren");

        addButton.setToolTipText("Neues Rezept hinzufügen");
        editButton.setToolTipText("Ausgewähltes Rezept bearbeiten");
        deleteButton.setToolTipText("Ausgewähltes Rezept löschen");

        Dimension buttonSize = new Dimension(160, 25);
        addButton.setPreferredSize(buttonSize);
        editButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        clearButton.setPreferredSize(buttonSize);







        // Konfiguration der Buttons
        addButton.setPreferredSize(buttonSize);
        addButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        addButton.setVerticalTextPosition(SwingConstants.CENTER);
        editButton.setPreferredSize(buttonSize);
        editButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        editButton.setVerticalTextPosition(SwingConstants.CENTER);
        deleteButton.setPreferredSize(buttonSize);
        deleteButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        deleteButton.setVerticalTextPosition(SwingConstants.CENTER);
        clearButton.setPreferredSize(buttonSize);
        clearButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        clearButton.setVerticalTextPosition(SwingConstants.CENTER);


        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

// Buttons unter dem Formular
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        formPanel.add(buttonPanel, gbc);

// Fügen wir das Formular zum Hauptfenster hinzu.
        add(formPanel, BorderLayout.SOUTH);

        // Buttonerreignisse
        addButton.addActionListener(e -> addRezept());
        editButton.addActionListener(e -> updateRezept());

        deleteButton.addActionListener(e -> deleteRezept());


        clearButton.addActionListener(e -> {
            patientComboBox.setSelectedIndex(0);
            medikamentComboBox.setSelectedIndex(0);
            dosierungField.setText("");
            startDatumField.setText("");
            endDatumField.setText("");
            bemerkungField.setText("");
        });

        // Daten laden
        loadRezepteData();
        loadPatientenData();
        loadMedikamenteData();
    }

    public void refreshPatientenComboBox(List<Patient> patients) {
        patientComboBox.removeAllItems(); // Aktuelle Liste löschen
        for (Patient patient : patients) {
            patientComboBox.addItem(patient); // Aktualisierte Patientenliste hinzufügen
        }
    }


    private void loadRezepteData() {
        try {
            tableModel.setRowCount(0); // Bereinigung der Tabelle
            List<Rezept> rezepte = rezeptDAO.getAllRezepte();
            for (Rezept rezept : rezepte) {
                tableModel.addRow(new Object[]{
                        rezept.getRezeptId(),
                        rezept.getPatientName(),
                        rezept.getMedikamentName(),
                        rezept.getDosierung(),
                        rezept.getStartdatum(),
                        rezept.getEnddatum(),
                        rezept.getBemerkung()
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Laden der Rezepte: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadPatientenData() {
        try {
            List<Patient> patients = rezeptDAO.getAllPatients();
            for (Patient patient : patients) {
                patientComboBox.addItem(patient);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Laden der Patienten: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadMedikamenteData() {
        try {
            List<Medikament> medikamente = rezeptDAO.getAllMedikamente();
            for (Medikament medikament : medikamente) {
                medikamentComboBox.addItem(medikament);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Laden der Medikamente: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillFormFromTable() {
        int selectedRow = rezepteTable.getSelectedRow(); // Abrufen des Indexes der ausgewählten Zeile
        System.out.println("Выбрана строка: " + selectedRow); // Debug-Ausgabe
        if (selectedRow != -1) {
            // Abrufen von Daten aus der Tabelle
            String patientName = (String) rezepteTable.getValueAt(selectedRow, 1); // "Patient"
            String medikamentName = (String) rezepteTable.getValueAt(selectedRow, 2); // "Medikament"
            String dosierung = (String) rezepteTable.getValueAt(selectedRow, 3); //  "Dosierung"
            String startdatum = (String) rezepteTable.getValueAt(selectedRow, 4); //  "Startdatum"
            String enddatum = (String) rezepteTable.getValueAt(selectedRow, 5); // "Enddatum"
            String bemerkung = (String) rezepteTable.getValueAt(selectedRow, 6); //  "Bemerkung"

            System.out.println("Daten ");
            System.out.println("Patient: " + patientName);
            System.out.println("Medikament: " + medikamentName);
            System.out.println("Dosierung: " + dosierung);
            System.out.println("Startdatum: " + startdatum);
            System.out.println("Enddatum: " + enddatum);
            System.out.println("Bemerkung: " + bemerkung);

            // Setzen der Werte in die ComboBox für den Patienten
            for (int i = 0; i < patientComboBox.getItemCount(); i++) {
                Patient patient = (Patient) patientComboBox.getItemAt(i);
                if (patientName.contains(patient.getVorname()) && patientName.contains(patient.getNachname())) {
                    patientComboBox.setSelectedItem(patient);
                    break;
                }
            }

            // Setzen der Werte in die ComboBox für den Patienten
            for (int i = 0; i < medikamentComboBox.getItemCount(); i++) {
                Medikament medikament = (Medikament) medikamentComboBox.getItemAt(i);
                if (medikament.getName().equals(medikamentName)) {
                    medikamentComboBox.setSelectedItem(medikament);
                    break;
                }
            }

            // Setzen der Werte in die Textfelder
            dosierungField.setText(dosierung != null ? dosierung : "");
            startDatumField.setText(startdatum != null ? startdatum : "");
            endDatumField.setText(enddatum != null ? enddatum : "");
            bemerkungField.setText(bemerkung != null ? bemerkung : "");
        }
    }

    private void addRezept() {
        try {
            Patient selectedPatient = (Patient) patientComboBox.getSelectedItem();
            Medikament selectedMedikament = (Medikament) medikamentComboBox.getSelectedItem();
            String dosierung = dosierungField.getText();
            String startdatum = startDatumField.getText();
            String enddatum = endDatumField.getText();
            String bemerkung = bemerkungField.getText();

            Rezept rezept = new Rezept(0, selectedPatient.getPatientId(), selectedMedikament.getMedikamentId(), dosierung, startdatum, enddatum, bemerkung);
            rezeptDAO.addRezept(rezept);
            JOptionPane.showMessageDialog(this, "Rezept hinzugefügt!");
            loadRezepteData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Hinzufügen des Rezepts: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void updateRezept() {
        int selectedRow = rezepteTable.getSelectedRow(); // Получить выбранную строку
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie ein Rezept aus, um es zu bearbeiten.", "Fehler", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Daten aus dem Formular abrufen
            Patient selectedPatient = (Patient) patientComboBox.getSelectedItem();
            Medikament selectedMedikament = (Medikament) medikamentComboBox.getSelectedItem();
            String dosierung = dosierungField.getText();
            String startdatum = startDatumField.getText();
            String enddatum = endDatumField.getText();
            String bemerkung = bemerkungField.getText();

            // Abrufen der Rezept-ID aus der Tabelle
            int rezeptId = (int) tableModel.getValueAt(selectedRow, 0);

            // Ein Rezept-Objekt mit aktualisierten Daten erstellen
            Rezept rezept = new Rezept(rezeptId, selectedPatient.getPatientId(), selectedMedikament.getMedikamentId(), dosierung, startdatum, enddatum, bemerkung);

            // Das Rezept in der Datenbank aktualisieren
            rezeptDAO.updateRezept(rezept);

            JOptionPane.showMessageDialog(this, "Rezept wurde erfolgreich aktualisiert!");
            loadRezepteData(); // Daten aktualisieren
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Aktualisieren des Rezepts: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Das Rezept in der Datenbank aktualisieren
    private void deleteRezept() {
        int selectedRow = rezepteTable.getSelectedRow(); // Die ausgewählte Zeile abrufen
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie ein Rezept aus, um es zu löschen.", "Fehler", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Die Rezept-ID aus der Tabelle abrufen
        int rezeptId = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Sind Sie sicher, dass Sie dieses Rezept löschen möchten?",
                "Löschen bestätigen",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                rezeptDAO.deleteRezept(rezeptId); // Das Rezept aus der Datenbank löschen
                JOptionPane.showMessageDialog(this, "Rezept wurde erfolgreich gelöscht!");
                loadRezepteData(); // Die Daten in der Tabelle aktualisieren
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Fehler beim Löschen des Rezepts: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}



