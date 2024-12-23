import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GUI {
    private JPanel mainPanel; // Hauptpanel
    private JTextField nameField;
    private JTextField ageField;
    private JButton saveButton;
    private JButton showButton;

    private BiConsumer<String, String> saveAction;
    private Consumer<Void> showAction;

    public GUI() {
        // Panel mit Layout
        mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        // Komponenten erstellen
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel ageLabel = new JLabel("Alter:");
        ageField = new JTextField();
        saveButton = new JButton("Speichern");
        showButton = new JButton("Anzeigen");

        // Aktionen für Buttons
        saveButton.addActionListener(e -> {
            if (saveAction != null) {
                saveAction.accept(nameField.getText(), ageField.getText());
            }
        });

        showButton.addActionListener(e -> {
            if (showAction != null) {
                showAction.accept(null);
            }
        });

        // Komponenten hinzufügen
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(ageLabel);
        mainPanel.add(ageField);
        mainPanel.add(saveButton);
        mainPanel.add(showButton);
    }

    // Setter für Aktionen
    public void setSaveAction(BiConsumer<String, String> saveAction) {
        this.saveAction = saveAction;
    }

    public void setShowAction(Consumer<Void> showAction) {
        this.showAction = showAction;
    }

    // Methode, um das Hauptpanel zu bekommen
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
