package com.spring.boot.services;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    public boolean sendEmail(String to, String message, String subject) throws MessagingException {

        boolean f = false;
        String from = "testwithveer@gmail.com";
        Properties prop = System.getProperties();

        //setting imp info
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.auth", "true");


        //step 1: get session Object

        Session session = Session.getInstance(prop, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("testwithveer@gmail.com", "mutefsaltcblpfxy");
            }

        });


        session.setDebug(true);


        //step 2: compose Email
        MimeMessage mimeMessage = new MimeMessage(session);


        try {
            //from
            mimeMessage.setFrom(from);

            //add recipient to message
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //add subject
            mimeMessage.setSubject(subject);

            //add message

            //Attachment
            // String path = "C:\\Users\\viren\\Documents\\images\\web.jpg";
            mimeMessage.setText(message);
            // MimeMultipart mimeMultipart = new MimeMultipart();

            // MimeBodyPart mimetext = new MimeBodyPart();

            // MimeBodyPart mimefile = new MimeBodyPart();

            // try {
            //     mimetext.setText(message);

            //     File file = new File(path);

            //     mimefile.attachFile(file);
            //     mimeMultipart.addBodyPart(mimetext);
            //     mimeMultipart.addBodyPart(mimefile);

            // } catch (Exception e) {
            //     e.printStackTrace();
            // }
            // mimeMessage.setContent(mimeMultipart);

            //step 3: send message using transport service
            Transport.send(mimeMessage);

            System.out.println("Message is send successfully!!!");
            f=true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }
}
