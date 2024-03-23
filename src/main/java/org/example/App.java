package org.example;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.Properties;

public class App
{
    public static void main( String[] args )
    {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties,new MailAuthenticator());
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("apoorv.vardhman@gmail.com"));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress("apoorvaraj47@gmail.com"));
            message.setSubject("Jakarata Email Example");
            message.setText("Hurray! Email is coming from our application");
            Transport.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    static final class MailAuthenticator extends Authenticator{
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            Properties properties = new Properties();
            try {
                properties.load(App.class.getClassLoader().getResourceAsStream("mail.properties"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new PasswordAuthentication(properties.getProperty("mail.username"),properties.getProperty("mail.password"));
        }
    }
}
