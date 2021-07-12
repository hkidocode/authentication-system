package ma.pragmatic.authenticationsystem.service;

import ma.pragmatic.authenticationsystem.model.PasswordRequest;
import ma.pragmatic.authenticationsystem.model.User;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public interface UserService {
    void save(User user) throws FileNotFoundException;
    User getByEmail(String email);
    User getByEmailAndPassword(String email, String password);
    String confirmRegisterToken(String token);
    String confirmPasswordToken(String token);
    void sendResetPasswordMail(String email);
    void updatePassword(PasswordRequest passwordRequest);
}
