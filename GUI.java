import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private JPanel mainPanel; // Hauptpanel
    private JTextField nameField;
    private JTextField ageField;
    private JButton saveButton;
    private JButton cancelButton;

    public GUI() {
        // Panel mit Layout
        mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        // Komponenten erstellen
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel ageLabel = new JLabel("Alter:");
        ageField = new JTextField();
        saveButton = new JButton("Speichern");
        cancelButton = new JButton("Abbrechen");

        // Aktionen für Buttons
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String age = ageField.getText();
                JOptionPane.showMessageDialog(null, "Daten gespeichert:\nName: " + name + "\nAlter: " + age);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                ageField.setText("");
            }
        });

        // Komponenten hinzufügen
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(ageLabel);
        mainPanel.add(ageField);
        mainPanel.add(saveButton);
        mainPanel.add(cancelButton);
    }

    // Methode, um das Hauptpanel zu bekommen
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
