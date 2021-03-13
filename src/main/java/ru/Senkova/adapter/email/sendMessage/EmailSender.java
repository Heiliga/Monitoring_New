package ru.Senkova.adapter.email.sendMessage;

import java.io.File;
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
import static ru.Senkova.exception.ResponseCodeException.NOT_FOUND_FILE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.Senkova.adapter.rest.service.dto.SendEmailFormDto;
import ru.Senkova.configuration.mail.EmailSenderConfiguration;
import ru.Senkova.exception.EmailSenderException;

@Service("emailSender")
public class EmailSender{

    @Autowired
    private EmailSenderConfiguration emailSenderConfiguration;
    private Session session;
    private String username;
    private String password;

    public EmailSender() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", emailSenderConfiguration.getHost());
        properties.put("mail.smtp.port", emailSenderConfiguration.getPort());
        properties.put("mail.smtp.auth", emailSenderConfiguration.getAuth());
        properties.put("mail.smtp.starttls.enable", emailSenderConfiguration.getStartTTlsEnabled());
        username = emailSenderConfiguration.getUsername();
        password = emailSenderConfiguration.getPassword();

        session = Session.getInstance(properties,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
    }

    public void sendEmail(SendEmailFormDto dto){

        try {
            MimeMessage message = buildMessage(dto);
            Transport.send(message);

        } catch (MessagingException | IOException mex ) { mex.printStackTrace(); } //todo

    }

    private MimeMessage buildMessage(SendEmailFormDto dto) throws MessagingException,IOException{
        MimeMessage message = new MimeMessage(session);

        InternetAddress internetAddress = new InternetAddress(username);
        internetAddress.setPersonal("NEWS");

        message.setFrom(internetAddress);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(dto.getEmailUser()));
        message.setSubject(dto.getLogin()+", не пропусти новую статью");

        Multipart mp = new MimeMultipart();

        if(new File(PATH + "/html/index.html").exists())
           throw new IOException(NOT_FOUND_FILE.getMessage());

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
        return message;
    }
}

