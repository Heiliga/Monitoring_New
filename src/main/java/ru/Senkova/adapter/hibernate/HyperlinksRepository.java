package ru.Senkova.adapter.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Senkova.domain.Hyperlinks;

@Repository
public interface HyperlinksRepository extends JpaRepository<Hyperlinks, Long> {
    boolean existsByLink(String link);
     Hyperlinks findByLink(String link);
}
