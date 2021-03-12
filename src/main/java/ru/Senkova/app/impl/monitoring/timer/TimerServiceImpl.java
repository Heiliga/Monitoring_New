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

    private HyperlinksUsers hyperlinksUsers;
    private Timer timer;
    private long period;

    private SendEmailFormDto dto;

    private PageTitles pageTitles;


    {
        period = 2000;
    }

    public TimerServiceImpl() {
    }

    public TimerServiceImpl(SendEmailFormDto dto, PageTitles pageTitles, HyperlinksUsers hyperlinksUsers) {
        this.dto=dto;
        this.pageTitles=pageTitles;
        this.hyperlinksUsers=hyperlinksUsers;
    }


    //    @Scheduled(cron = )
    public void monitoring(){
        if(timer != null) {
            timer.cancel();
            return;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Elements elementsArticle =  pageTitles.getElementsArticles();
                int size=elementsArticle.size();
                int i=0;
                while(elementsArticle.get(size-i).attr("href")!=pageTitles.getLinkLastElement()){
                    PageTitleAndDate pageTitleAndDate=new PageTitleAndDate(elementsArticle.get(size-i).attr("href"));
                    dto.setDate(pageTitleAndDate.getTime());
                    dto.setArticle(pageTitleAndDate.getArticle());
                    dto.setLinkArticle(pageTitleAndDate.getUrl());
                    EmailSender emailSender=new EmailSender();
                    emailSender.sendEmail(dto);
                    i++;
                }
                hyperlinksUsers.setLinkArticle(pageTitles.getLinkLastElement());
                hyperlinksUsersRepository.save(hyperlinksUsers);
            }
        }, 1000, period);

    }

}
