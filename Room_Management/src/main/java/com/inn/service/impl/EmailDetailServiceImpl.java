package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.inn.constant.RoomConstant;
import com.inn.model.EmailDetails;
import com.inn.service.IEmailDetailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailDetailServiceImpl implements IEmailDetailService {

  @Value("${spring.mail.username}")
  private String sender;

  @Value("${spring.mail.password}")
  private String password;

  @Value("${spring.mail.host}")
  private String host;

  @Value("${spring.mail.port}")
  private String port;

  @Override
  public String sendMailWithOutAttachment(EmailDetails details) {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "sendSimpleMail of EmailDetailServiceImpl {}", kv("Details", details));
      log.info("Value of sender and pasword :{} {}", sender, password);
      Session session = createSession();
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(sender));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(details.getRecipient()));
      message.setSubject(details.getSubject());
      message.setText(details.getMsgBody());
      Transport.send(message);
      log.info("Email sent successfully.......!!!");
      return "Email sent successfully.";
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  Session createSession() {
    log.info("Iside the createSession method");
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", port);
    return Session.getInstance(props, new javax.mail.Authenticator() {
      protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication(sender, password);
      }
    });

  }

  @Override
  public String sendMailWithAttachment(EmailDetails details) throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "sendMailWithAttachment of EmailDetailServiceImpl {}",  kv("Details", details));
    try {
      log.info("Value of sender and pasword :{} {}", sender, password);
      Session session = createSession();
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(sender));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(details.getRecipient()));
      message.setSubject(details.getSubject());
      BodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setText(details.getMsgBody());
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);
      messageBodyPart = new MimeBodyPart();
      String filename = details.getAttachment();
      DataSource source = new FileDataSource(filename);
      messageBodyPart.setDataHandler(new DataHandler(source));
      messageBodyPart.setFileName(filename);
      multipart.addBodyPart(messageBodyPart);
      message.setContent(multipart);
      Transport.send(message);
      log.info("Email sent successfully.......");
      return "Email sent successfully.......";
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

}
