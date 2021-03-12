package ru.Senkova.adapter.email.sendMessage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import static ru.Senkova.adapter.email.PartHTML.getPartCodeHtml;
import static ru.Senkova.app.impl.ConstVariable.PATH;
import ru.Senkova.adapter.rest.service.dto.SendEmailFormDto;


public class EmailSender{
    private final Properties properties = new Properties();

    private final String username = "system.monitoring.new@gmail.com";
    private final String password = "";
    private final String from = username;

    {
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
    }

    public EmailSender() {
    }

    public void sendEmail(SendEmailFormDto dto){

        Session session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);

            InternetAddress internetAddress = new InternetAddress("system.monitoring.new@gmail.com");
            internetAddress.setPersonal("NEWS");

            message.setFrom(internetAddress);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(dto.getEmailUser()));
            message.setSubject(dto.getLogin()+", не пропусти новую статью");


            Multipart mp = new MimeMultipart();

            Scanner inFile = new Scanner(Paths.get(PATH + "/html/index.html"));
            MimeBodyPart htmlPart = new MimeBodyPart();

            MimeBodyPart imagePart = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(PATH+"/image/logo.png");
            imagePart.setDataHandler(new DataHandler(fds));
            imagePart.setHeader("Content-ID", "some_image_id");

            htmlPart.setText(getPartCodeHtml(dto,inFile),"UTF-8", "html");

            mp.addBodyPart(imagePart);
            mp.addBodyPart(htmlPart);

            message.setContent(mp);
            Transport.send(message);

        } catch (MessagingException | IOException mex ){ mex.printStackTrace(); }

    }

}

