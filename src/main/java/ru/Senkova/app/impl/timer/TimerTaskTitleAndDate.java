package ru.Senkova.app.impl.timer;

import ru.Senkova.app.impl.pages.PageTitleAndTime;

import java.util.TimerTask;

public class TimerTaskTitleAndDate extends TimerTask {

    private PageTitleAndTime pageTitleAndTime;

    public TimerTaskTitleAndDate(PageTitleAndTime pageTitleAndTime) {
        this.pageTitleAndTime = pageTitleAndTime;
    }

    @Override
    public void run() {

    }
}
