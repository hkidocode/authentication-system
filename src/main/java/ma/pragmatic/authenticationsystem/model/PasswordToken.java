package ma.pragmatic.authenticationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "PASSWORD_TOKEN")
@Data @NoArgsConstructor @AllArgsConstructor
public class PasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Long idToken;

    @Column(name="confirmation_token", nullable = false)
    private String token;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "expire_date", nullable = false)
    private LocalDateTime expireDate;

    @Column(name = "confirmed_date")
    private LocalDateTime confirmDate;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "id_user")
    private User user;

    public PasswordToken(User user) {
        this.token = UUID.randomUUID().toString();
        this.createDate = LocalDateTime.now();
        this.expireDate = LocalDateTime.now().plusMinutes(60);
        this.user = user;
    }


}
