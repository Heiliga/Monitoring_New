package ru.Senkova.adapter.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Senkova.domain.Hyperlinks;
import ru.Senkova.domain.HyperlinksUsers;
import ru.Senkova.domain.HyperlinksUsersPK;
import ru.Senkova.domain.UserApp;

import java.util.Optional;
import java.util.Set;

@Repository
public interface HyperlinksUsersRepository extends JpaRepository<HyperlinksUsers, HyperlinksUsersPK> {
    Optional<HyperlinksUsers>findAllByKeyWord(String keyWord);
    Set<HyperlinksUsers> findAllByKeyWordAndUserApp(String keyWord, UserApp userapp);
    boolean existsByUserAppAndKeyWord(String keyWord,UserApp userApp);
    boolean existsByHyperlinksAndUserAppAndKeyWord(Hyperlinks hyperlinksUsers, UserApp userApp, String keyWord);
    void deleteByHyperlinksAndUserAppAndKeyWord(Hyperlinks hyperlinksUsers, UserApp userApp, String keyWord);
}
