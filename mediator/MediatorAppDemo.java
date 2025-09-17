package mediator;

public class MediatorAppDemo {
    public static void main(String[] args) {
        FormMediator mediator = new FormMediator();

        TextField usernameField = new TextField(mediator);
        TextField passwordField = new TextField(mediator);
        Button loginButton = new Button(mediator);
        Label statusLabel = new Label(mediator);

        mediator.setUsernameField(usernameField);
        mediator.setPasswordField(passwordField);
        mediator.setLoginButton(loginButton);
        mediator.setStatusLabel(statusLabel);

        // Simulate user interaction
        usernameField.setText("admin");
        passwordField.setText("1234");
        loginButton.click(); // Should succeed

        System.out.println("\n--- New Attempt with Wrong Password ---");
        passwordField.setText("wrong");
        loginButton.click(); // Should fail

        // Textfield updated: admin
        // Textfield updated: 1234
        // Login button clicked
        // Status: ✅ Login successful!

        // --- New Attempt with Wrong Password ---
        // Textfield updated: wrong
        // Login button clicked
        // Status: ❌ Invalid credentials.
    }
}
