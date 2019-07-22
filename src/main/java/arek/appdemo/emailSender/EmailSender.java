package arek.appdemo.emailSender;

public interface EmailSender {
    void sendMail (String to, String subject, String content);
}
