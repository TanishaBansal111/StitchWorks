package com.example.taliorproject.emails;


import java.io.File;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

public class emailController {

    public static void SendMail(String to, String Subj, String emailBody) {
        String from = "tanishabansalqueen@gmail.com"; // sender's email
        final String username = "tanishabansalqueen@gmail.com"; // your Gmail address
        final String password = "qdip edjj ttgj kndt"; // your app password

        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(Subj);
            message.setText(emailBody);
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void main(String args[])
//    {
//        SendMail("pankajbansal11992@gmail.com","Java","Hello Java");
//    }
}
