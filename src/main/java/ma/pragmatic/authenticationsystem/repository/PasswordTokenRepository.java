package ma.pragmatic.authenticationsystem.repository;

import ma.pragmatic.authenticationsystem.model.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {
    Optional<PasswordToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE PasswordToken p " + "SET p.confirmDate = ?1 " + "WHERE p.token = ?2")
    int updateConfirmDate(LocalDateTime confirmDate, String token);
}
