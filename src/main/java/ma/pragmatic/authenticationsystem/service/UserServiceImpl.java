package ma.pragmatic.authenticationsystem.service;

import ma.pragmatic.authenticationsystem.exception.EmailExistException;
import ma.pragmatic.authenticationsystem.exception.EntityNotExistException;
import ma.pragmatic.authenticationsystem.exception.PasswordNotMatchException;
import ma.pragmatic.authenticationsystem.model.Role;
import ma.pragmatic.authenticationsystem.model.User;
import ma.pragmatic.authenticationsystem.repository.RoleRepository;
import ma.pragmatic.authenticationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotExistException("User does not exist"));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User addOrUpdate(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByName("USER");
            roles.add(role);
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        throw new EmailExistException("Email already in use!");
    }

    @Override
    public void deleteById(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new EntityNotExistException("User does not exist");
        }
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
                return user;
            } else {
                throw new PasswordNotMatchException("Incorrect password!");
            }
        } else {
            throw new EntityNotExistException("Incorrect email!");
        }
    }

}
