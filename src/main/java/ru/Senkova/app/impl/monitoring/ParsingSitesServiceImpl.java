package ru.Senkova.app.impl.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Senkova.adapter.hibernate.HyperlinksRepository;
import ru.Senkova.adapter.hibernate.HyperlinksUsersRepository;
import ru.Senkova.adapter.rest.service.dto.SendEmailFormDto;
import ru.Senkova.app.api.ParsingSitesService;
import ru.Senkova.app.api.UserAppService;
import ru.Senkova.app.impl.monitoring.pages.PageSearch;
import ru.Senkova.app.impl.monitoring.pages.PageTitles;
import ru.Senkova.app.impl.monitoring.timer.TimerServiceImpl;
import ru.Senkova.domain.Hyperlinks;
import ru.Senkova.domain.HyperlinksUsers;
import ru.Senkova.domain.UserApp;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import static ru.Senkova.app.impl.ValidateVariable.VALID_URL_HREF_ALL;
import static ru.Senkova.app.impl.ValidateVariable.VALID_URL_HREF_DOMAIN;

@Transactional
@Service("parsingSitesService")
public class ParsingSitesServiceImpl implements ParsingSitesService {

    @Autowired
    private HyperlinksUsersRepository hyperlinksUsersRepository;

    @Autowired
    private HyperlinksRepository hyperlinksRepository;

    @Autowired
    private UserAppService userAppService;

    @Autowired
    private TimerServiceImpl timerService;

    @Override
    public void saveHyperlinksUsers(UserApp userApp, Set<String> urls, String keyWord) {
        Set<Hyperlinks> hashSetHyperlinks = getHyperlinks(urls);
        for (Hyperlinks hyperlinks : hashSetHyperlinks) {
            saveOneHyperlinksUsers(hyperlinks, userApp, keyWord);
        }
    }

    @Override
    public void saveLinkTitles(HyperlinksUsers hyperlinksUsers) {
        PageSearch pageSearch = new PageSearch(hyperlinksUsers.getHyperlinks().getLink());
        hyperlinksUsers.setLinkTitles(pageSearch.buildUrlPageTitle(hyperlinksUsers.getKeyWord()));
        hyperlinksUsersRepository.save(hyperlinksUsers);
    }

    @Override
    public void saveLinkArticle(HyperlinksUsers hyperlinksUsers) {
        PageTitles pageTitles = new PageTitles(hyperlinksUsers.getLinkTitles());
        hyperlinksUsers.setLinkTitles(pageTitles.getUrl());
        hyperlinksUsers.setLinkArticle(pageTitles.getLinkLastElement(hyperlinksUsers.getHyperlinks().getLink()));
        hyperlinksUsersRepository.save(hyperlinksUsers);
    }


    @Override
    public void startMonitoring(UserApp userApp, String keyWord) {
        Set<HyperlinksUsers> allHyperlinksUsers = findHyperlinksUserByUserAppAndKeyWord(userApp, keyWord);
        for (HyperlinksUsers hyperlinksUsers : allHyperlinksUsers) {
            saveLinkTitles(hyperlinksUsers);
            saveLinkArticle(hyperlinksUsers);

            SendEmailFormDto dto = new SendEmailFormDto();
            dto.setLogin(userApp.getLogin());
            dto.setFirstName(userApp.getFirstName());
            dto.setPatronymic(userApp.getPatronymic());
            dto.setEmailUser(userApp.getEmail());
            dto.setLinkSite(hyperlinksUsers.getHyperlinks().getLink());
            dto.setKeyWord(keyWord);

            timerService.monitoring(dto);
        }
    }

    @Override
    public void finishMonitoring(UserApp userApp, String keyWord) {
        Set<HyperlinksUsers> allHyperlinksUsers = findHyperlinksUserByUserAppAndKeyWord(userApp, keyWord);
        for (HyperlinksUsers hyperlinksUsers : allHyperlinksUsers) {
            timerService.finishMonitoring();
            deleteOneHyperlinksUsers(hyperlinksUsers);
        }
    }


    public Set<HyperlinksUsers> findHyperlinksUserByUserAppAndKeyWord(UserApp userApp, String keyWord) {
        return hyperlinksUsersRepository.findByKeyWordAndUserApp(keyWord, userApp);
    }

    public UserApp findUserAppByLogin(String login) {
        return userAppService.findByLogin(login);
    }

    public boolean validateUrls(Set<String> urls) {
        for (String url : urls) {
            if (!(url.matches(VALID_URL_HREF_ALL) || url.matches(VALID_URL_HREF_DOMAIN))) {
                return false;
            }
        }
        return true;
    }
    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private String transformateUrl(String url) {
        if (url.matches(VALID_URL_HREF_ALL)) {
            return url.trim();
        } else {
            return "http://" + url.trim();
        }
    }

    private void saveOneHyperlinksUsers(Hyperlinks hyperlinks, UserApp userApp, String keyWord) { //todo передавать DTO вместо entity
        HyperlinksUsers hyperlinksUsers = new HyperlinksUsers(userApp, hyperlinks, keyWord);
        if(hyperlinksUsersRepository.existsByHyperlinksAndUserAppAndKeyWord(hyperlinks,userApp,keyWord))
            deleteOneHyperlinksUsers(hyperlinksUsers);
        hyperlinksUsersRepository.save(hyperlinksUsers);
    }


//    todo
//    Dto dto;
//    Entity entity = repository.findById(dto.getId());

//    Dto dto
//    Entity entity = new Entity();
//    entity.setParam(dto.getParam());
//    repository.save(entity);

//    Entity entity = repository.findById(id);
//    Dto dto = new Dto();
//    dto.setParam(entity.getParam());

    private void deleteOneHyperlinksUsers(HyperlinksUsers hyperlinksUsers) { //todo dto
        if (hyperlinksUsersRepository
            .existsByHyperlinksAndUserAppAndKeyWord(hyperlinksUsers.getHyperlinks(), hyperlinksUsers.getUserApp(), hyperlinksUsers.getKeyWord())) {
            hyperlinksUsersRepository.deleteAllByHyperlinksAndUserAppAndKeyWord
                (hyperlinksUsers.getHyperlinks(), hyperlinksUsers.getUserApp(), hyperlinksUsers.getKeyWord());
        }
    }

    private void deleteOneHyperlinks(Hyperlinks hyperlinks) { //todo dto
        if (hyperlinksRepository.existsById(hyperlinks.getId())) {
            hyperlinksRepository.deleteById(hyperlinks.getId());
        }
    }

    private Set<Hyperlinks> getHyperlinks(Set<String> urls) { //todo dto
        Set<Hyperlinks> hashSetHyperlinks = new HashSet<>();
        for (String url : urls) {
            Hyperlinks hyperlinks = new Hyperlinks(transformateUrl(url));
            if (!hyperlinksRepository.existsByLink(transformateUrl(url))) {
                hyperlinksRepository.save(hyperlinks);
            }
            hashSetHyperlinks.add(hyperlinksRepository.findByLink(transformateUrl(url)));
        }
        return hashSetHyperlinks;
    }
}