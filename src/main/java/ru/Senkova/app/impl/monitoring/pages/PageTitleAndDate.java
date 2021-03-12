package ru.Senkova.app.impl.monitoring.pages;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.Senkova.app.impl.GetElementsTags;

import java.sql.Timestamp;

import static ru.Senkova.app.impl.ValidateVariable.VALID_TAG_FOR_DATE;

public class PageTitleAndDate extends ObjectDocument {

    private Timestamp datetime;
    private String article;

    public PageTitleAndDate(String link) {
        super(link);
    }

    public String getTime() {
        Document doc = getDoc();
        Element tagTime = GetElementsTags.getElementsForDate(getDoc(),VALID_TAG_FOR_DATE);
        return tagTime.text();
    }

    public String getArticle() {
        Document doc = getDoc();
        Element tagArticle = GetElementsTags.getElementsForTitle(getDoc());
        return tagArticle.text();
    }

}
