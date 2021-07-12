package ma.pragmatic.authenticationsystem.repository;

import ma.pragmatic.authenticationsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u " + "SET u.isEnabled = true WHERE u.email = ?1")
    int enableUser(String email);
}
