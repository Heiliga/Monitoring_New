package ru.Senkova.app.impl.pages;

import java.util.Date;

public class PageTitleAndTime extends ObjectDocument {

    private Date time;
    private String article;
    private PageTitles pageTitles;

    public PageTitleAndTime(PageTitles pageTitles) {
        super(pageTitles.getLinkLastElement().attr("href"));
        this.pageTitles=pageTitles;
    }

    public Date getTime() {

    }

    public String getArticle() {
        return article;
    }


}
