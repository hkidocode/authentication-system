package ma.pragmatic.authenticationsystem.repository;

import ma.pragmatic.authenticationsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
