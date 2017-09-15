package com.shc.scinventory.enterpriseShippingToolJobs.Clients;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


@Component
public class SmtpClient {
  private static final Logger LOG = Logger.getLogger(SmtpClient.class);
  private String logEmailTemplate;
  private String logRowTemplate;

  @Autowired
  MailSender mailSender;

  public SmtpClient() {
    LOG.info("smtpClient instantiated");
  }

//  public String getLogEmailTemplate() {
//    if (logEmailTemplate == null) {
//      this.setLogEmailTemplate(readEmailTemplate("emailTemplates/email.html"));
//    }
//    return logEmailTemplate;
//  }

  public void setLogEmailTemplate(String logEmailTemplate) {
    this.logEmailTemplate = logEmailTemplate;
  }

//  public String getLogRowTemplate() {
//    if (this.logRowTemplate == null) {
//      this.setLogRowTemplate(readEmailTemplate("emailTemplates/logMessage.html"));
//    }
//    return logRowTemplate;
//  }

  public void setLogRowTemplate(String logRowTemplate) {
    this.logRowTemplate = logRowTemplate;
  }

  public void send(String message) {
    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo("Kevin.Caravaggio@searshc.com");
    email.setFrom("ESOC@searshc.com");
    email.setSubject("Enterprise Shipping Tool");
    email.setText(message);
    try {
      if (mailSender == null) LOG.info("MailSender null");
      mailSender.send(email);
    } catch (MailException e) {
      LOG.error(e);
    }
  }
  
  public void send(String message, String receiver, String title) {
	    SimpleMailMessage email = new SimpleMailMessage();
	    email.setTo(receiver);
	    email.setFrom("ESOC@searshc.com");
	    email.setSubject(title);
	    email.setText(message);
	    try {
	      if (mailSender == null) LOG.info("MailSender null");
	      mailSender.send(email);
	    } catch (MailException e) {
	      LOG.error(e);
	    }
	  }
  
  @Autowired
	private JavaMailSender javaMailSender;

  public void sendFile (String msg, String receiver, String title, String fileName) {
	   MimeMessage message = javaMailSender.createMimeMessage();

	   try{
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
	
			helper.setFrom("ESOC@searshc.com");
			helper.setTo(receiver);
			helper.setSubject(title);
			helper.setText(msg);
	
			FileSystemResource file = new FileSystemResource(fileName);
			helper.addAttachment(file.getFilename(), file);

	   }catch (MessagingException e) {
	    	 throw new MailParseException(e);
	   }
	   javaMailSender.send(message);
  }

  // Derive email alerts from change results
  public void sendLogMessage(String dcUnitId, String body) throws MessagingException {
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
      @Override
      public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        message.setCc("Kevin.Caravaggio@searshc.com");
        message.setTo("ESOC@searshc.com");
        message.setFrom("ESOC@searshc.com");
        // messageHelper.setCc("Cheetah_Management@searshc.com");
        message.setSubject(String.format("Cheetah Tool: %s updated", dcUnitId));
        message.setText(body, true);  // HTML email
      }
    };
    ((JavaMailSenderImpl) mailSender).send(preparator);
    LOG.info("Notification email sent");
  }

  // Create an HTML-based email message notifying changes
//  public String createLogEmail(List<Log> logs) {
//    if (logs == null || logs.isEmpty()) {
//      return null;
//    }
//    String template = this.getLogEmailTemplate();
//    String bodyTemplate = this.getLogRowTemplate();
//
//    // Marshal new changelog entries to HTML
//    StringBuilder buil = new StringBuilder();
//    for (Log log : logs) {
//      buil.append(String.format(bodyTemplate,
//          log.getUser(),
//          log.getField(),
//          log.getStoreType(),
//          log.getDcUnitId(),
//          log.getOldValue(),
//          log.getNewValue(),
//          log.getDatetime()));
//    }
//    String rows = buil.toString();
//    return template.replace("{{changes}}", rows);
//  }

  // Take in HTML email content
//  private String readEmailTemplate(String filename) {
//    StringBuilder buil = new StringBuilder();
//    try (InputStream template = this.getClass().getClassLoader().getResourceAsStream(filename)) {
//      try (BufferedReader reader = new BufferedReader(new InputStreamReader(template))) {
//        String line;
//        while ((line = reader.readLine()) != null) {
//          buil.append(line);
//        }
//      }
//    } catch (IOException e) {
//      LOG.warn("Message template unavailable");
//    }
//    return buil.toString();
//  }
}
