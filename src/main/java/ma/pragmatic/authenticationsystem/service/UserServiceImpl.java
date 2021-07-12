package ma.pragmatic.authenticationsystem.service;

import ma.pragmatic.authenticationsystem.config.mail.EmailSender;
import ma.pragmatic.authenticationsystem.exception.*;
import ma.pragmatic.authenticationsystem.model.*;
import ma.pragmatic.authenticationsystem.repository.RoleRepository;
import ma.pragmatic.authenticationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private PasswordTokenService passwordTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSender emailSender;

    private User user;

    @Override
    public void save(User user) throws FileNotFoundException {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByName("USER");
            roles.add(role);
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenService.addConfirmationToken(confirmationToken);

            String link = "http://localhost:8181/register/confirm?token=" + confirmationToken.getToken();
            // create templateModel for thymeleaf template
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("firstName", user.getFirstName());
            templateModel.put("registerDate", new Date());
            templateModel.put("link", link);
            emailSender.sendWithAttachment(user.getEmail(), "Email Verification", "email-body.html", templateModel, "Spring Security", ResourceUtils.getFile("classpath:static/attachment.png"));
        }
        throw new EmailExistException("Email already in use!");
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                if (user.isEnabled()) {
                    return user;
                } else {
                    throw new UserNotVerifiedException("Your account is disabled. Please confirm your email!");
                }
            } else {
                throw new PasswordNotMatchException("Incorrect password!");
            }
        } else {
            throw new EntityNotExistException("Incorrect email!");
        }
    }

    @Override
    public String confirmRegisterToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() ->
                        new TokenNotFoundException("Token does not found"));

        if (confirmationToken.getConfirmDate() != null) {
            throw new TokenConfirmedException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpireDate();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userRepository.enableUser(confirmationToken.getUser().getEmail());
        return "Confirmed token";
    }

    @Override
    public String confirmPasswordToken(String token) {
        PasswordToken passwordToken = passwordTokenService.getToken(token)
                .orElseThrow(() ->
                        new TokenNotFoundException("Token does not found"));

        if (passwordToken.getConfirmDate() != null) {
            throw new TokenConfirmedException("Password already confirmed");
        }

        LocalDateTime expiredAt = passwordToken.getExpireDate();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token expired");
        }

        passwordTokenService.setConfirmedAt(token);
        return "Confirmed token";
    }

    @Override
    public void updatePassword(PasswordRequest passwordRequest) {
        if (passwordRequest.getPassword().equals(passwordRequest.getMatchingPassword())) {
            user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        } else {
            throw new PasswordNotMatchException("Password does not match");
        }
    }

    @Override
    public void sendResetPasswordMail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            PasswordToken passwordToken = new PasswordToken(user);
            passwordTokenService.addPasswordToken(passwordToken);
            this.user = user;
            // create templateModel for thymeleaf template
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("firstName", user.getFirstName());
            templateModel.put("link", "http://localhost:8181/reset-password?token=" + passwordToken.getToken());
            emailSender.sendWithoutAttachment(user.getEmail(), "Reset Password", "reset-password.html", templateModel);
        } else {
            throw new EmailNotExistException("Email does not exist");
        }
    }

}
