package ru.Senkova.app.impl.monitoring.timer;

import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.Senkova.adapter.email.sendMessage.EmailSender;
import ru.Senkova.adapter.hibernate.HyperlinksRepository;
import ru.Senkova.adapter.hibernate.HyperlinksUsersRepository;
import ru.Senkova.adapter.hibernate.UserAppRepository;
import ru.Senkova.adapter.rest.service.dto.SendEmailFormDto;
import ru.Senkova.app.api.TimerService;
import ru.Senkova.app.impl.monitoring.pages.PageTitleAndDate;
import ru.Senkova.app.impl.monitoring.pages.PageTitles;
import ru.Senkova.domain.Hyperlinks;
import ru.Senkova.domain.HyperlinksUsers;
import ru.Senkova.domain.UserApp;

import java.util.Timer;
import java.util.TimerTask;

@Service("timerService")
public class TimerServiceImpl implements TimerService {

    @Autowired
    private HyperlinksUsersRepository hyperlinksUsersRepository;

    @Autowired
    private HyperlinksRepository hyperlinksRepository;

    @Autowired
    private UserAppRepository userAppRepository;

    @Autowired
    private EmailSender emailSender;

    private Timer timer;
    private long period;

    {
        period = 4000000;
    }

    //    @Scheduled(cron = )
    public void monitoring(SendEmailFormDto dto) {

         timer = new Timer();
         timer.schedule(new TimerTask() {
             @Override
             public void run() {
                 Hyperlinks hyperlinks=hyperlinksRepository.findByLink(dto.getLinkSite());
                 UserApp userApp = userAppRepository.findByLogin(dto.getLogin());

                 HyperlinksUsers hyperlinksUsers = hyperlinksUsersRepository.findByHyperlinksAndUserAppAndKeyWord(hyperlinks, userApp, dto.getKeyWord());

                 PageTitles pageTitles = new PageTitles(hyperlinksUsers.getLinkTitles());
                 Elements elementsArticle = pageTitles.getElementsArticles(hyperlinks.getLink());
                 int i = 0;
                 while (elementsArticle.get(i).attr("href").equals(pageTitles.getLinkLastElement(hyperlinks.getLink()))
                     ||  i<1)//elementsArticle.size())
                 {
                     String link = elementsArticle.get(i).attr("href");
                     PageTitleAndDate pageTitleAndDate = new PageTitleAndDate(link);
                     dto.setDate(pageTitleAndDate.getTime());
                     dto.setArticle(pageTitleAndDate.getArticle());
                     dto.setLinkArticle(pageTitleAndDate.getUrl());
                     emailSender.sendEmail(dto);
                     i++;
                 }
                 hyperlinksUsers.setLinkArticle(elementsArticle.get(i).attr("href"));
                 hyperlinksUsersRepository.save(hyperlinksUsers); }
            }, 1L, 4000000L);
    }

    public void finishMonitoring() {
        if (timer!=null) {
            timer.cancel();
            return;
        }
    }
}
