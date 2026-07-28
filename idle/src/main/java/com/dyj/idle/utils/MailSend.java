package com.dyj.idle.utils;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class MailSend {
    private static String lastCode;

    public static String sendEmail(String to) throws MessagingException {
        String code = generateVerificationCode(6);
        send(to, "Verification Code", code);
        lastCode = code;
        return code;
    }

    public static boolean check(String code) {
        return lastCode != null && lastCode.equals(code);
    }

    public static String generateVerificationCode(int length) {
        String chars = "0123456789";
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            verificationCode.append(chars.charAt(random.nextInt(chars.length())));
        }
        return verificationCode.toString();
    }

    public static String sendaccount(String to, String account) throws MessagingException {
        send(to, "Account Registration", account);
        lastCode = account;
        return account;
    }

    private static void send(String to, String subject, String content) throws MessagingException {
        final String sslFactory = "javax.net.ssl.SSLSocketFactory";
        final String smtpHost = getenvOrDefault("MAIL_HOST", "smtp.qq.com");
        final String username = requiredEnv("MAIL_USERNAME");
        final String password = requiredEnv("MAIL_PASSWORD");
        final String from = getenvOrDefault("MAIL_FROM", username);
        final String port = getenvOrDefault("MAIL_PORT", "465");

        Properties props = new Properties();
        props.setProperty("mail.smtp.socketFactory.class", sslFactory);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.socketFactory.port", port);
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.username", username);
        props.put("mail.smtp.password", password);

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(to)});
        message.setSubject(subject);
        message.setSentDate(new Date());
        message.setText(content);

        Transport transport = session.getTransport("smtp");
        transport.connect(smtpHost, username, password);
        transport.send(message);
        transport.close();
    }

    private static String requiredEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing environment variable: " + name);
        }
        return value;
    }

    private static String getenvOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
