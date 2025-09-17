package mediator;

class TextField extends UIComponentBase {
    private String text = "";

    public TextField(IUIMediator mediator) {
        super(mediator);
    }

    public void setText(String newText) {
        this.text = newText;
        System.out.println("Textfield updated: " + newText);
        notifyMediator();
    }

    public String getText() {
        return text;
    }
}

class Button extends UIComponentBase {
    private boolean isEnabled = false;

    public Button(IUIMediator mediator) {
        super(mediator);
    }

    public void click() {
        if (isEnabled) {
            System.out.println("Login button clicked");
            notifyMediator();
        } else {
            System.out.println("Login button was disabled!");
        }
    }

    public void setEnabled(boolean value) {
        this.isEnabled = value;
    }
}

class Label extends UIComponentBase {
    private String text;

    public Label(IUIMediator mediator) {
        super(mediator);
    }

    public void setText(String message) {
        this.text = message;
        System.out.println("Status: " + text);
    }
}
