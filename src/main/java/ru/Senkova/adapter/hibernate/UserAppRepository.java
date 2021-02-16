package ru.Senkova.adapter.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Senkova.domain.UserApp;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, Long> {
    boolean existsByLogin(String login);

    UserApp findByLogin(String login);

    boolean existsByEmail(String email);
}
