package ma.pragmatic.authenticationsystem.service;

import ma.pragmatic.authenticationsystem.model.PasswordToken;
import ma.pragmatic.authenticationsystem.repository.PasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordTokenService {

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    public void addPasswordToken(PasswordToken passwordToken) {
        passwordTokenRepository.save(passwordToken);
    }

    public Optional<PasswordToken> getToken(String token) {
        return passwordTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return passwordTokenRepository.updateConfirmDate(LocalDateTime.now(), token);
    }


}
