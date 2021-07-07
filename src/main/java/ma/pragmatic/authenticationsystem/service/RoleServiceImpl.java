package ma.pragmatic.authenticationsystem.service;

import ma.pragmatic.authenticationsystem.exception.EntityNotExistException;
import ma.pragmatic.authenticationsystem.model.Role;
import ma.pragmatic.authenticationsystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getById(Integer roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotExistException("Role does not exist"));
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role addOrUpdate(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteById(Integer roleId) {
        if (roleRepository.findById(roleId).isPresent()) {
            roleRepository.deleteById(roleId);
        } else {
            throw new EntityNotExistException("Role does not exist");
        }
    }
}
