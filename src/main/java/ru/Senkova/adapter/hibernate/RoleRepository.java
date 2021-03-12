package ru.Senkova.adapter.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Senkova.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
