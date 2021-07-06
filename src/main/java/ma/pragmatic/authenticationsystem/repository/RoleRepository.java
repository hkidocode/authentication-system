package ma.pragmatic.authenticationsystem.repository;

import ma.pragmatic.authenticationsystem.model.ERole;
import ma.pragmatic.authenticationsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(ERole role);
}
