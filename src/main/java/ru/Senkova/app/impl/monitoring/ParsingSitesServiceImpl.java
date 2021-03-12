package ru.Senkova.app.impl.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Senkova.adapter.hibernate.HyperlinksRepository;
import ru.Senkova.adapter.hibernate.HyperlinksUsersRepository;
import ru.Senkova.adapter.hibernate.UserAppRepository;
import ru.Senkova.adapter.rest.service.dto.SendEmailFormDto;
import ru.Senkova.app.api.ParsingSitesService;

import ru.Senkova.app.impl.monitoring.pages.PageSearch;
import ru.Senkova.app.impl.monitoring.pages.PageTitleAndDate;
import ru.Senkova.app.impl.monitoring.pages.PageTitles;
import ru.Senkova.app.impl.monitoring.timer.TimerServiceImpl;
import ru.Senkova.domain.Hyperlinks;
import ru.Senkova.domain.HyperlinksUsers;
import ru.Senkova.domain.UserApp;

import java.util.HashSet;
import java.util.Set;

import static ru.Senkova.app.impl.ValidateVariable.*;

@Service("parsingSitesService")
public class ParsingSitesServiceImpl implements ParsingSitesService {

    @Autowired
    private HyperlinksUsersRepository hyperlinksUsersRepository;

    @Autowired
    private HyperlinksRepository hyperlinksRepository;

    @Autowired
    private UserAppRepository userAppRepository;

    @Autowired
    private TimerServiceImpl timerService;

    private PageSearch pageSearch;
    private PageTitles pageTitles;
    private PageTitleAndDate pageTitleAndTime;

    public void saveHyperlinksUsers(UserApp userApp, Set<String> urls, String keyWord) {
        Set<Hyperlinks> hashSetHyperlinks = getHyperlinks(urls);
        for (Hyperlinks hyperlinks : hashSetHyperlinks)
            saveOneHyperlinksUsers(hyperlinks,userApp,keyWord);
    }

    public void saveLink(String keyWord, UserApp userApp) {
        Set<HyperlinksUsers> allHyperlinksUsers = findHyperlinksUserByUserAppAndKeyWord(userApp,keyWord);
        for (HyperlinksUsers hyperlinksUsers : allHyperlinksUsers) {
            pageSearch = new PageSearch(hyperlinksUsers.getHyperlinks().getLink());
            pageTitles = new PageTitles(pageSearch,keyWord);
            hyperlinksUsers.setLinkArticle(pageTitles.getLinkLastElement());
            hyperlinksUsersRepository.save(hyperlinksUsers);
        }
    }

    public void deleteHyperlinksUsers(UserApp userApp, String keyWord) {
        Set<HyperlinksUsers> allHyperlinksUsers = findHyperlinksUserByUserAppAndKeyWord(userApp,keyWord);
        for (HyperlinksUsers hyperlinksUsers : allHyperlinksUsers) {
            Hyperlinks hyperlinks = hyperlinksUsers.getHyperlinks();
            deleteOneHyperlinksUsers(hyperlinksUsers);
            deleteOneHyperlinks(hyperlinks);
        }
    }

    public void startMonitoring(UserApp userApp, String keyWord){
        Set<HyperlinksUsers> allHyperlinksUsers = findHyperlinksUserByUserAppAndKeyWord(userApp,keyWord);
        for (HyperlinksUsers hyperlinksUsers : allHyperlinksUsers){
            SendEmailFormDto dto=new SendEmailFormDto();
            dto.setLogin(userApp.getLogin());
            dto.setFirstName(userApp.getFirstName());
            dto.setPatronymic(userApp.getPatronymic());
            dto.setEmailUser(userApp.getEmail());
            dto.setLinkSite(hyperlinksUsers.getHyperlinks().getLink());
            dto.setKeyWord(keyWord);

            timerService=new TimerServiceImpl(dto,pageTitles,hyperlinksUsers);
            timerService.monitoring();
        }
    }

    public void finishMonitoring(UserApp userApp, String keyWord){
        Set<HyperlinksUsers> allHyperlinksUsers = findHyperlinksUserByUserAppAndKeyWord(userApp,keyWord);
        for (HyperlinksUsers hyperlinksUsers : allHyperlinksUsers){
            timerService.monitoring();
        }
    }

    public Set<HyperlinksUsers> findHyperlinksUserByUserAppAndKeyWord(UserApp userApp, String keyWord){
        return hyperlinksUsersRepository.findAllByKeyWordAndUserApp(keyWord, userApp);
    }

    public UserApp findUserAppByLogin(String login){
        return userAppRepository.findByLogin(login);
    }

    public boolean validateUrls(Set<String> urls){
        for(String url : urls){
            if(!(url.matches(VALID_URL_HREF_ALL)||url.matches(VALID_URL_HREF_DOMAIN)))
                return false;
        }
        return true;
    }
// ===================================================================================================================
// = Implementation
// ===================================================================================================================


    private String transformationUrl(String url){
        if(url.matches(VALID_URL_HREF_ALL))
            return url.trim();
        else return "http://"+url.trim();
    }
    //метод для преобразования url

    private void saveOneHyperlinksUsers(Hyperlinks hyperlinks, UserApp userApp, String keyWord) {
        HyperlinksUsers hyperlinksUsers = new HyperlinksUsers(userApp, hyperlinks, keyWord);
        hyperlinksUsersRepository.save(hyperlinksUsers);
    }


    private void deleteOneHyperlinksUsers(HyperlinksUsers hyperlinksUsers){
        if(hyperlinksUsersRepository.existsByHyperlinksAndUserAppAndKeyWord(hyperlinksUsers.getHyperlinks(),hyperlinksUsers.getUserApp(),hyperlinksUsers.getKeyWord()))
            hyperlinksUsersRepository.deleteByHyperlinksAndUserAppAndKeyWord
                    (hyperlinksUsers.getHyperlinks(), hyperlinksUsers.getUserApp(), hyperlinksUsers.getKeyWord());
    }

    private void deleteOneHyperlinks(Hyperlinks hyperlinks){
        if(hyperlinksRepository.existsById(hyperlinks.getId()))
            hyperlinksRepository.deleteById(hyperlinks.getId());

    }

    private Set<Hyperlinks> getHyperlinks(Set<String> urls) {
        Set<Hyperlinks> hashSetHyperlinks = new HashSet<>();
        for (String url : urls) {
            Hyperlinks hyperlinks = new Hyperlinks(transformationUrl(url));
            if (!hyperlinksRepository.existsByLink(transformationUrl(url))) {
                hyperlinksRepository.save(hyperlinks);
            }
            hashSetHyperlinks.add(hyperlinks);
        }
        return hashSetHyperlinks;
    }
}