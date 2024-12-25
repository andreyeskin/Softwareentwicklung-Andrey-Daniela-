import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

public class LoginGUI {
    private JFrame loginFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;

    private BiConsumer<String, String> loginAction;

    public LoginGUI() {
        loginFrame = new JFrame("Login");
        loginFrame.setLayout(new BorderLayout());
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 300);
        loginFrame.setLocationRelativeTo(null); // Zentriert das Fenster

        // Hintergrundbild laden und anzeigen
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundIcon = OptimizedImageLoader.loadImage(
                        "/Users/andrey.eskin/IdeaProjects/Softwareentwicklung-Andrey-Daniela-/src/src/Lib/Von-Papier-zu-Pixel-E-Health-in-Arztpraxen-1.jpg.png", 400, 300
                );
                if (backgroundIcon != null) {
                    g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username-Feld
        JLabel usernameLabel = new JLabel("Benutzername:");
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundPanel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30));
        usernameField.setBackground(Color.WHITE);
        gbc.gridx = 1;
        backgroundPanel.add(usernameField, gbc);

        // Passwort-Feld
        JLabel passwordLabel = new JLabel("Passwort:");
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        backgroundPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setBackground(Color.WHITE);
        gbc.gridx = 1;
        backgroundPanel.add(passwordField, gbc);

        // Login-Button
        loginButton = new JButton("Einloggen");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Platzierung des Cursors
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(loginButton, gbc);

        // Nachricht (Fehler oder Erfolg)
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        backgroundPanel.add(messageLabel, gbc);

        // Aktion für den Login-Button
        loginButton.addActionListener(e -> {
            if (loginAction != null) {
                loginAction.accept(usernameField.getText().trim(), new String(passwordField.getPassword()));
            }
        });

        loginFrame.add(backgroundPanel, BorderLayout.CENTER);
    }

    public void setLoginAction(BiConsumer<String, String> action) {
        this.loginAction = action;
    }

    public void showMessage(String message) {
        messageLabel.setText(message);
    }

    public void show() {
        loginFrame.setVisible(true);
    }

    public void close() {
        loginFrame.dispose();
    }

    /**
     * Hilfsklasse zum effizienten Laden von Bildern
     */
    static class OptimizedImageLoader {
        public static ImageIcon loadImage(String path, int width, int height) {
            try {
                ImageIcon icon = new ImageIcon(path);
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            } catch (Exception e) {
                System.err.println("Fehler beim Laden des Bildes: " + e.getMessage());
                return null;
            }
        }
    }
}