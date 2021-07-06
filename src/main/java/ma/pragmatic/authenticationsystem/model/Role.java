package ma.pragmatic.authenticationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ROLES")
@Data @NoArgsConstructor @AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role", updatable = false, nullable = false)
    private Integer idRole;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ERole role;


}
