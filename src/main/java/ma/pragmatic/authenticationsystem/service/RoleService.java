package ma.pragmatic.authenticationsystem.service;

import ma.pragmatic.authenticationsystem.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    Role getById(Integer roleId);
    List<Role> getAll();
    Role addOrUpdate(Role role);
    void deleteById(Integer roleId);
}
