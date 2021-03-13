package ru.Senkova.adapter.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Senkova.domain.Hyperlinks;
import ru.Senkova.domain.HyperlinksUsers;
import ru.Senkova.domain.UserApp;

import java.util.Set;

@Repository
public interface HyperlinksUsersRepository extends JpaRepository<HyperlinksUsers, Long> {
    Set<HyperlinksUsers> findByKeyWordAndUserApp(String keyWord, UserApp userapp);
    boolean existsByHyperlinksAndUserAppAndKeyWord(Hyperlinks hyperlinksUsers, UserApp userApp, String keyWord);
    void deleteAllByHyperlinksAndUserAppAndKeyWord(Hyperlinks hyperlinksUsers, UserApp userApp, String keyWord);
    HyperlinksUsers findByHyperlinksAndUserAppAndKeyWord(Hyperlinks hyperlinksUsers, UserApp userApp, String keyWord);
}
