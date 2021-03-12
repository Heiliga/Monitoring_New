package ru.Senkova.app.api;

import ru.Senkova.domain.HyperlinksUsers;
import ru.Senkova.domain.UserApp;

import java.util.Set;

public interface ParsingSitesService {
    void saveHyperlinksUsers(UserApp userApp, Set<String> urls, String keyWord);
    void saveLink(String keyWord, UserApp userApp);
    void deleteHyperlinksUsers(UserApp userApp, String keyWord);
    void startMonitoring(UserApp userApp, String keyWord);
    void finishMonitoring(UserApp userApp, String keyWord);
    Set<HyperlinksUsers> findHyperlinksUserByUserAppAndKeyWord(UserApp userApp, String keyWord);
    UserApp findUserAppByLogin(String login);
    boolean validateUrls(Set<String> urls);
}
