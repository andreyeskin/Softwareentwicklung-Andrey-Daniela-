// Clase GUI.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * Die Klasse GUI bietet die grafische Benutzeroberfläche für die Patientenverwaltung.
 */
public class GUI {
    private JPanel mainPanel;
    private JTextField vornameField;
    private JTextField nachnameField;
    private JTextField geschlechtField;
    private JTextField adresseField;
    private JTextField alterField;
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private JButton saveButton;
    private JButton refreshButton;

    private Consumer<String[]> saveAction;
    public Runnable refreshAction;

    public GUI() {
        mainPanel = new JPanel(new BorderLayout());

        // Eingabefelder
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        vornameField = new JTextField();
        nachnameField = new JTextField();
        geschlechtField = new JTextField();
        adresseField = new JTextField();
        alterField = new JTextField();

        inputPanel.add(new JLabel("Vorname:"));
        inputPanel.add(vornameField);
        inputPanel.add(new JLabel("Nachname:"));
        inputPanel.add(nachnameField);
        inputPanel.add(new JLabel("Geschlecht:"));
        inputPanel.add(geschlechtField);
        inputPanel.add(new JLabel("Adresse:"));
        inputPanel.add(adresseField);
        inputPanel.add(new JLabel("Alter:"));
        inputPanel.add(alterField);

        // Buttons
        saveButton = new JButton("Speichern");
        refreshButton = new JButton("Aktualisieren");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(refreshButton);

        // Tabelle
        tableModel = new DefaultTableModel(new String[]{"ID", "Vorname", "Nachname", "Geschlecht", "Adresse", "Alter"}, 0);
        patientTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(patientTable);

        // Aktionen
        saveButton.addActionListener(e -> {
            if (saveAction != null) {
                try {
                    saveAction.accept(new String[]{
                            vornameField.getText().trim(),
                            nachnameField.getText().trim(),
                            geschlechtField.getText().trim(),
                            adresseField.getText().trim(),
                            alterField.getText().trim()
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

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(tableScroll, BorderLayout.SOUTH);
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

    public JPanel getMainPanel() {
        return mainPanel;
    }
}