package ru.Senkova.app.impl.timer;

import ru.Senkova.app.impl.State;
import ru.Senkova.app.impl.pages.PageTitleAndTime;

import java.util.Timer;

public class MonitoringServiceImpl {

    private Timer timer;
    private TimerTaskTitleAndDate timerTaskTitleAndDate;
    private long period;

    {
        period = 2000;
    }

    public MonitoringServiceImpl() {
    }

    public void monitoring(PageTitleAndTime pageTitleAndTime){
        if(timer != null)
            timer.cancel();
        timer = new Timer();
        TimerTaskTitleAndDate timerTaskTitleAndDate = new TimerTaskTitleAndDate(pageTitleAndTime);
        timer.schedule(timerTaskTitleAndDate, period);

    }

}
