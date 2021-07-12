package ma.pragmatic.authenticationsystem.config.mail;

import java.io.File;
import java.util.Map;

public interface EmailSender {
    void sendWithoutAttachment(String email, String subject, String thymeleafFileName, Map<String, Object> templateModel);
    void sendWithAttachment(String email, String subject, String thymeleafFileName, Map<String, Object> templateModel, String attachmentFileName, File file);
}
