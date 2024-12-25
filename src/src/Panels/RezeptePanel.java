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

    public RezeptePanel() throws SQLException {
        rezeptDAO = new RezeptDAO();
        setLayout(new BorderLayout());

        // Таблица рецептов
        tableModel = new DefaultTableModel(new String[]{"RezeptID", "Patient", "Medikament", "Dosierung", "Startdatum", "Enddatum", "Bemerkung"}, 0);
        rezepteTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(rezepteTable);
        add(scrollPane, BorderLayout.CENTER);


        rezepteTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Убедимся, что событие завершено
                fillFormFromTable(); // Метод для заполнения формуляра
            }
        });


        // Формуляр для добавления/редактирования
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Поля ввода
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Patient:"), gbc);

        gbc.gridx = 1;
        patientComboBox = new JComboBox<>();
        formPanel.add(patientComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Medikament:"), gbc);

        gbc.gridx = 1;
        medikamentComboBox = new JComboBox<>();
        formPanel.add(medikamentComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Dosierung:"), gbc);

        gbc.gridx = 1;
        dosierungField = new JTextField();
        formPanel.add(dosierungField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Startdatum (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        startDatumField = new JTextField();
        formPanel.add(startDatumField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Enddatum (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        endDatumField = new JTextField();
        formPanel.add(endDatumField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Bemerkung:"), gbc);

        gbc.gridx = 1;
        bemerkungField = new JTextField();
        formPanel.add(bemerkungField, gbc);

        // Кнопки
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Hinzufügen");
        JButton editButton = new JButton("Bearbeiten");
        JButton deleteButton = new JButton("Löschen");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.SOUTH);

        // События кнопок
        addButton.addActionListener(e -> addRezept());
        editButton.addActionListener(e -> updateRezept());

        deleteButton.addActionListener(e -> deleteRezept());

        // Загрузка данных
        loadRezepteData();
        loadPatientenData();
        loadMedikamenteData();
    }

    public void refreshPatientenComboBox(List<Patient> patients) {
        patientComboBox.removeAllItems(); // Очистить текущий список
        for (Patient patient : patients) {
            patientComboBox.addItem(patient); // Добавить обновленный список пациентов
        }
    }


    private void loadRezepteData() {
        try {
            tableModel.setRowCount(0); // Очистка таблицы
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
        int selectedRow = rezepteTable.getSelectedRow(); // Получаем индекс выбранной строки
        System.out.println("Выбрана строка: " + selectedRow); // Отладочный вывод
        if (selectedRow != -1) {
            // Получаем данные из таблицы
            String patientName = (String) rezepteTable.getValueAt(selectedRow, 1); // Столбец "Patient"
            String medikamentName = (String) rezepteTable.getValueAt(selectedRow, 2); // Столбец "Medikament"
            String dosierung = (String) rezepteTable.getValueAt(selectedRow, 3); // Столбец "Dosierung"
            String startdatum = (String) rezepteTable.getValueAt(selectedRow, 4); // Столбец "Startdatum"
            String enddatum = (String) rezepteTable.getValueAt(selectedRow, 5); // Столбец "Enddatum"
            String bemerkung = (String) rezepteTable.getValueAt(selectedRow, 6); // Столбец "Bemerkung"

            System.out.println("Данные из таблицы: ");
            System.out.println("Patient: " + patientName);
            System.out.println("Medikament: " + medikamentName);
            System.out.println("Dosierung: " + dosierung);
            System.out.println("Startdatum: " + startdatum);
            System.out.println("Enddatum: " + enddatum);
            System.out.println("Bemerkung: " + bemerkung);

            // Устанавливаем значения в ComboBox для пациента
            for (int i = 0; i < patientComboBox.getItemCount(); i++) {
                Patient patient = (Patient) patientComboBox.getItemAt(i);
                if (patientName.contains(patient.getVorname()) && patientName.contains(patient.getNachname())) {
                    patientComboBox.setSelectedItem(patient);
                    break;
                }
            }

            // Устанавливаем значения в ComboBox для медикамента
            for (int i = 0; i < medikamentComboBox.getItemCount(); i++) {
                Medikament medikament = (Medikament) medikamentComboBox.getItemAt(i);
                if (medikament.getName().equals(medikamentName)) {
                    medikamentComboBox.setSelectedItem(medikament);
                    break;
                }
            }

            // Устанавливаем значения в текстовые поля
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
            // Получить данные из формуляра
            Patient selectedPatient = (Patient) patientComboBox.getSelectedItem();
            Medikament selectedMedikament = (Medikament) medikamentComboBox.getSelectedItem();
            String dosierung = dosierungField.getText();
            String startdatum = startDatumField.getText();
            String enddatum = endDatumField.getText();
            String bemerkung = bemerkungField.getText();

            // Получить ID рецепта из таблицы
            int rezeptId = (int) tableModel.getValueAt(selectedRow, 0);

            // Создать объект Rezept с обновленными данными
            Rezept rezept = new Rezept(rezeptId, selectedPatient.getPatientId(), selectedMedikament.getMedikamentId(), dosierung, startdatum, enddatum, bemerkung);

            // Обновить рецепт в базе данных
            rezeptDAO.updateRezept(rezept);

            JOptionPane.showMessageDialog(this, "Rezept wurde erfolgreich aktualisiert!");
            loadRezepteData(); // Перезагрузить данные
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Aktualisieren des Rezepts: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }


        // Реализация удаления рецепта
        private void deleteRezept() {
            int selectedRow = rezepteTable.getSelectedRow(); // Получить выбранную строку
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie ein Rezept aus, um es zu löschen.", "Fehler", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Получить ID рецепта из таблицы
            int rezeptId = (int) tableModel.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Sind Sie sicher, dass Sie dieses Rezept löschen möchten?",
                    "Löschen bestätigen",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    rezeptDAO.deleteRezept(rezeptId); // Удалить рецепт из базы данных
                    JOptionPane.showMessageDialog(this, "Rezept wurde erfolgreich gelöscht!");
                    loadRezepteData(); // Обновить данные в таблице
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Fehler beim Löschen des Rezepts: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }




