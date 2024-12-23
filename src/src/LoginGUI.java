import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

/**
 * Die Klasse LoginGUI bietet eine grafische Benutzeroberfläche für den Login.
 */
public class LoginGUI {
    private JFrame loginFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;

    private BiConsumer<String, String> loginAction;

    public LoginGUI() {
        loginFrame = new JFrame("Login");
        loginFrame.setLayout(new GridBagLayout());
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 250);
        loginFrame.setLocationRelativeTo(null); // Zentriert das Fenster

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginFrame.add(new JLabel("Benutzername:"), gbc);

        usernameField = new JTextField();
        gbc.gridx = 1;
        loginFrame.add(usernameField, gbc);

        // Passwort
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginFrame.add(new JLabel("Passwort:"), gbc);

        passwordField = new JPasswordField();
        gbc.gridx = 1;
        loginFrame.add(passwordField, gbc);

        // Login-Button
        loginButton = new JButton("Einloggen");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginFrame.add(loginButton, gbc);

        // Nachricht
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginFrame.add(messageLabel, gbc);

        // Aktion für Login-Button
        loginButton.addActionListener(e -> {
            if (loginAction != null) {
                loginAction.accept(usernameField.getText().trim(), new String(passwordField.getPassword()));
            }
        });
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
}
