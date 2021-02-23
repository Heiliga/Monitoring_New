package ru.Senkova.app.impl.pages;

import org.springframework.beans.factory.annotation.Autowired;
import ru.Senkova.adapter.hibernate.HyperlinksRepository;
import ru.Senkova.adapter.hibernate.HyperlinksUsersRepository;
import ru.Senkova.app.api.ParsingJsoupService;
import ru.Senkova.app.impl.pages.PageSearch;
import ru.Senkova.app.impl.pages.PageTitles;
import ru.Senkova.domain.Hyperlinks;
import ru.Senkova.domain.HyperlinksUsers;
import ru.Senkova.domain.UserApp;
import ru.Senkova.exception.ParsingSoupException;

import java.util.HashSet;
import java.util.Set;

import static ru.Senkova.exception.ResponseCodeException.*;

public class ParsingJsoupServiceImpl implements ParsingJsoupService {

    @Autowired
    private HyperlinksUsersRepository hyperlinksUsersRepository;

    @Autowired
    private HyperlinksRepository hyperlinksRepository;


    public void saveHyperlinksUsers(UserApp userApp, Set<String> urls, String keyWord) {
        Set<Hyperlinks> hashSetHyperlinks = getHyperlinks(urls);
        for (Hyperlinks hyperlinks : hashSetHyperlinks) {
            if(!saveOneHyperlinksUsers(hyperlinks,userApp,keyWord))
                throw new ParsingSoupException(INT_CODE_10_NOT_SAVE_LINKS_USER);
        }
    }

/*    public void saveLink(String keyWord, UserApp userApp) {//спросить как здесь будет понимать пользователь +++ нормально ли создавать два объекта
        Set<HyperlinksUsers> allHyperlinksUsers = hyperlinksUsersRepository.findAllByKeyWordAndUserApp(keyWord, userApp);//придумать исключение;
        for (HyperlinksUsers hyperlinksUsers : allHyperlinksUsers) {
            PageSearch pageSearch = new PageSearch(hyperlinksUsers.getHyperlinks().getLink());
            PageTitles pageTitles = new PageTitles(pageSearch.buildUrlPageTitle(hyperlinksUsers.getKeyWord()));
            hyperlinksUsers.setLinkTitles(pageTitles.getLinkTitles());
            hyperlinksUsers.setLastElements(pageTitles.getLinkLastElement());
        }
    }*/

    public void deleteHyperlinksUsers(UserApp userApp, String keyWord) {
        Set<HyperlinksUsers> allHyperlinksUsers = hyperlinksUsersRepository.findAllByKeyWordAndUserApp(keyWord, userApp);
        for (HyperlinksUsers hyperlinksUsers : allHyperlinksUsers)
            if(!deleteOneHyperlinksUsers(hyperlinksUsers))
                throw new ParsingSoupException(INT_CODE_3_NOT_DELETE_LINKS);

    }



// ===================================================================================================================
// = Implementation
// ===================================================================================================================

    //метод для преобразования url

    private boolean saveOneHyperlinksUsers(Hyperlinks hyperlinks, UserApp userApp, String keyWord) {
        HyperlinksUsers hyperlinksUsers = new HyperlinksUsers(userApp, hyperlinks, keyWord);
        hyperlinksUsersRepository.save(hyperlinksUsers);
        if (hyperlinksUsersRepository.existsByHyperlinksAndUserAppAndKeyWord(hyperlinks, userApp, keyWord))
            return true;
        else return false;
    }


    private boolean deleteOneHyperlinksUsers(HyperlinksUsers hyperlinksUsers){
        if(hyperlinksUsersRepository.existsByHyperlinksAndUserAppAndKeyWord(hyperlinksUsers.getHyperlinks(),hyperlinksUsers.getUserApp(),hyperlinksUsers.getKeyWord())) {
            hyperlinksUsersRepository.deleteByHyperlinksAndUserAppAndKeyWord
                    (hyperlinksUsers.getHyperlinks(), hyperlinksUsers.getUserApp(), hyperlinksUsers.getKeyWord());
            return true;
        }
        return false;
    }

    private Set<Hyperlinks> getHyperlinks(Set<String> urls) {//подумать над модификатором доступа данного метода
        //метод для преобразования url
        Set<Hyperlinks> hashSetHyperlinks = new HashSet<>();
        for (String url : urls) {
            Hyperlinks hyperlinks = new Hyperlinks(url);
            if (!hyperlinksRepository.existsByLink(url)) {
                hyperlinksRepository.save(hyperlinks);
            }
            hashSetHyperlinks.add(hyperlinks);
        }
        return hashSetHyperlinks;
    }
}