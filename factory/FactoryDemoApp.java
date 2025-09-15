package factory;

public class FactoryDemoApp {
    public static void main(String[] args) {
        NotificationCreator creator;

        // Send email
        creator = new EmailNotificationCreator();
        creator.send("Welcome to learning desing patterns 101");

        // Send sms
        creator = new SMSNotificationCreator();
        creator.send("Your OTP is 12345");

        // Send push notification
        creator = new PushNotificationCreator();
        creator.send("Outlook meeting in 15m");
    }
}
