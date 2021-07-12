package ma.pragmatic.authenticationsystem.config.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Service
public class EmailService implements EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Value("${spring.mail.username}")
    private String from;

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);


    @Async
    public void sendWithAttachment(String email, String subject, String thymeleafFileName, Map<String, Object> templateModel,
                                   String attachmentFileName, File file) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(templateModel);
            mimeMessageHelper.setText(springTemplateEngine.process(thymeleafFileName, thymeleafContext), true);
            mimeMessageHelper.setTo(email.split("[,;]"));
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.addAttachment(attachmentFileName, file);
            javaMailSender.send(mimeMessage);
            LOGGER.info("Email sent successfully!");

        } catch (MessagingException exception) {
            LOGGER.error("Failed to send email", exception);
            throw new IllegalStateException("Failed to send email");
        }

    }

    @Async
    public void sendWithoutAttachment(String email, String subject, String thymeleafFileName, Map<String, Object> templateModel) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(templateModel);
            mimeMessageHelper.setText(springTemplateEngine.process(thymeleafFileName, thymeleafContext), true);
            mimeMessageHelper.setTo(email.split("[,;]"));
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(from);
            javaMailSender.send(mimeMessage);
            LOGGER.info("Email sent successfully!");

        } catch (MessagingException exception) {
            LOGGER.error("Failed to send email", exception);
            throw new IllegalStateException("Failed to send email");
        }

    }
}
