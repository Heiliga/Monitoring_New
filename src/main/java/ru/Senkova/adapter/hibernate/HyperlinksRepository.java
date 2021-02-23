package ru.Senkova.adapter.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Senkova.domain.Hyperlinks;
import ru.Senkova.domain.HyperlinksUsersPK;
import ru.Senkova.domain.Role;

@Repository
public interface HyperlinksRepository extends JpaRepository<Hyperlinks, Long> {
    boolean existsByLink(String link);
}
