import export.ExportFunction;
import print.PrintFunction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GUI {
    private JPanel mainPanel;
    private JTextField anredeField;
    private JTextField vornameField;
    private JTextField nachnameField;
    private JTextField geburtsdatumField;
    private JTextField svnrField;
    private JTextField versicherungField;
    private JTextField telefonnummerField;
    private JTextField strasseField;
    private JTextField plzField;
    private JTextField ortField;
    private JTextField bundeslandField;
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private JButton saveButton;
    private JButton refreshButton;
    private JButton exportButton;
    private JButton printButton;

    private Consumer<String[]> saveAction;
    public Runnable refreshAction;

    public GUI() {
        // Hauptpanel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Eingabefelder (Formular)
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Patientendaten eingeben"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1;

        // Felder und Labels
        anredeField = new JTextField();
        vornameField = new JTextField();
        nachnameField = new JTextField();
        geburtsdatumField = new JTextField();
        svnrField = new JTextField();
        versicherungField = new JTextField();
        telefonnummerField = new JTextField();
        strasseField = new JTextField();
        plzField = new JTextField();
        ortField = new JTextField();
        bundeslandField = new JTextField();

        addFieldToPanel(inputPanel, "Anrede:", anredeField, gbc, 0);
        addFieldToPanel(inputPanel, "Vorname:", vornameField, gbc, 1);
        addFieldToPanel(inputPanel, "Nachname:", nachnameField, gbc, 2);
        addFieldToPanel(inputPanel, "Geburtsdatum (yyyy-MM-dd):", geburtsdatumField, gbc, 3);
        addFieldToPanel(inputPanel, "SVNR:", svnrField, gbc, 4);
        addFieldToPanel(inputPanel, "Versicherung:", versicherungField, gbc, 5);
        addFieldToPanel(inputPanel, "Telefonnummer:", telefonnummerField, gbc, 6);
        addFieldToPanel(inputPanel, "Straße:", strasseField, gbc, 7);
        addFieldToPanel(inputPanel, "PLZ:", plzField, gbc, 8);
        addFieldToPanel(inputPanel, "Ort:", ortField, gbc, 9);
        addFieldToPanel(inputPanel, "Bundesland:", bundeslandField, gbc, 10);

        // Buttons
        saveButton = new JButton("Speichern");
        refreshButton = new JButton("Aktualisieren");
        exportButton = new JButton("Exportieren");
        printButton = new JButton("Drucken");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(saveButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(printButton);

        // Tabelle
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Anrede", "Vorname", "Nachname", "Geburtsdatum", "SVNR", "Versicherung",
                        "Telefonnummer", "Straße", "PLZ", "Ort", "Bundesland"}, 0
        );
        patientTable = new JTable(tableModel);
        patientTable.setFillsViewportHeight(true);
        patientTable.setRowHeight(25);
        patientTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        patientTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        JScrollPane tableScroll = new JScrollPane(patientTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Patientenliste"));

        // Aktionen
        saveButton.addActionListener(e -> {
            if (saveAction != null) {
                try {
                    saveAction.accept(new String[]{
                            anredeField.getText().trim(),
                            vornameField.getText().trim(),
                            nachnameField.getText().trim(),
                            geburtsdatumField.getText().trim(),
                            svnrField.getText().trim(),
                            versicherungField.getText().trim(),
                            telefonnummerField.getText().trim(),
                            strasseField.getText().trim(),
                            plzField.getText().trim(),
                            ortField.getText().trim(),
                            bundeslandField.getText().trim()
                    });
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Ungültige Eingabe! Bitte alle Felder korrekt ausfüllen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        refreshButton.addActionListener(e -> {
            if (refreshAction != null) {
                refreshAction.run();
            }
        });

        exportButton.addActionListener(e -> {
            List<Object[]> tableData = getTableDataFromJTable();
            String[] columnNames = getColumnNamesFromJTable();
            ExportFunction.exportToCSV(tableData, columnNames, (JFrame) SwingUtilities.getWindowAncestor(mainPanel));
        });

        printButton.addActionListener(e -> {
            PrintFunction.printTable(patientTable, (JFrame) SwingUtilities.getWindowAncestor(mainPanel));
        });

        // Struktur
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(tableScroll, BorderLayout.SOUTH);
    }

    private void addFieldToPanel(JPanel panel, String labelText, JTextField textField, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(textField, gbc);
    }

    public void setSaveAction(Consumer<String[]> action) {
        this.saveAction = action;
    }

    public void setRefreshAction(Runnable action) {
        this.refreshAction = action;
    }

    public void updateTable(List<Object[]> data) {
        tableModel.setRowCount(0);
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    public List<Object[]> getTableDataFromJTable() {
        List<Object[]> data = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object[] row = new Object[tableModel.getColumnCount()];
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                row[j] = tableModel.getValueAt(i, j);
            }
            data.add(row);
        }
        return data;
    }

    public String[] getColumnNamesFromJTable() {
        int columnCount = tableModel.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = tableModel.getColumnName(i);
        }
        return columnNames;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
