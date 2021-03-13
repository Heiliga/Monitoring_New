package ru.Senkova.app.impl.monitoring.timer;

import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Senkova.adapter.email.sendMessage.EmailSender;
import ru.Senkova.adapter.hibernate.HyperlinksUsersRepository;
import ru.Senkova.adapter.rest.service.dto.SendEmailFormDto;
import ru.Senkova.app.api.TimerService;
import ru.Senkova.app.impl.monitoring.pages.PageTitleAndDate;
import ru.Senkova.app.impl.monitoring.pages.PageTitles;
import ru.Senkova.domain.HyperlinksUsers;

import java.util.Timer;
import java.util.TimerTask;

@Service("timerService")
public class TimerServiceImpl implements TimerService {

    @Autowired
    private HyperlinksUsersRepository hyperlinksUsersRepository;

    @Autowired
    private EmailSender emailSender;

    private HyperlinksUsers hyperlinksUsers;
    private Timer timer;
    private long period;

    private SendEmailFormDto dto;

    private PageTitles pageTitles;

    {
        period = 2000;
    }


    public TimerServiceImpl(SendEmailFormDto dto, PageTitles pageTitles, HyperlinksUsers hyperlinksUsers, HyperlinksUsersRepository hyperlinksUsersRepository) {
        this.dto = dto;
        this.pageTitles = pageTitles;
        this.hyperlinksUsers = hyperlinksUsers;
        this.hyperlinksUsers = hyperlinksUsers; //Todo Убрать конструктор. Перенести Атрибуты в метод
    }

    //    @Scheduled(cron = )
    public void monitoring() {
        if (timer != null) {
            finishMonitoring();
            return;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Elements elementsArticle = pageTitles.getElementsArticles();
                int size = elementsArticle.size();
                int i = 1;
                while (elementsArticle.get(size - i).attr("href").equals(pageTitles.getLinkLastElement())) {
                    String link = elementsArticle.get(size - i).attr("href");
                    PageTitleAndDate pageTitleAndDate = new PageTitleAndDate(link);
                    dto.setDate(pageTitleAndDate.getTime());
                    dto.setArticle(pageTitleAndDate.getArticle());
                    dto.setLinkArticle(pageTitleAndDate.getUrl());
                    emailSender.sendEmail(dto);
                    i++;
                }
                hyperlinksUsers.setLinkArticle(pageTitles.getLinkLastElement());
                hyperlinksUsersRepository.save(hyperlinksUsers);
            }
        }, 1000, period);
    }

    public void finishMonitoring() {
        timer.cancel();
    }
}
