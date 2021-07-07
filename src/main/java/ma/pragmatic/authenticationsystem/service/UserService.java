package ma.pragmatic.authenticationsystem.service;

import ma.pragmatic.authenticationsystem.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User getById(Long userId);
    List<User> getAll();
    User addOrUpdate(User user);
    void deleteById(Long userId);
    User getByEmail(String email);
    User getByEmailAndPassword(String email, String password);
}
