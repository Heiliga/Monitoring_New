package ru.Senkova.app.impl;

import javafx.scene.transform.Scale;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Senkova.app.impl.pages.PageSearch;
import ru.Senkova.app.impl.pages.PageTitles;

import java.util.Scanner;

public class ValidateVariable {
    public static final String VALID_TAG_FOR_PAGE_SEARCH_BY_CLASS = ".*search.*";
    public static final String VALID_TAG_FOR_PAGE_TITLE = ".*title.*";
    public static final String VALID_TAG_FOR_PAGE_TITLE_BY_TEXT = "[пП]о [вВДд][ра][те][ме]е?н?и?";
    public static final String VALID_TAG_FOR_PAGE_TITLE_BY_DATE = ".*date.*";
    public static final String VALID_TAG_FOR_PAGE_TITLE_BY_LENGTH = ".{10}";

    public static void main(String[] args){
        PageSearch pageSearch = new PageSearch("https://habr.com/ru");
        PageTitles pageTitles=new PageTitles(pageSearch,"Москва");
        pageTitles.getUrl();
        Elements rightTags = GetElementsTags.getElementsWithTitles(pageTitles.getDoc(),VALID_TAG_FOR_PAGE_TITLE);
        for (Element element: rightTags){
            System.out.println(element);
        }
    }
}
