package ru.Senkova.adapter.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Senkova.domain.Role;
import ru.Senkova.domain.RoleName;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAll();
    List<Role> findAllByName(String name);
}
