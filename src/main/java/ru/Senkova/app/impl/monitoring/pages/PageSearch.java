package ru.Senkova.app.impl.monitoring.pages;

import org.jsoup.nodes.Element;
import ru.Senkova.app.impl.GetElementsTags;
import ru.Senkova.exception.ParsingSoupException;

import static ru.Senkova.exception.ResponseCodeException.NOT_FOUND_INPUT;

public class PageSearch extends ObjectDocument {

    public static String startUrl; //todo убрать статику

    public PageSearch(String url) {
        super(url + "/search/");
        startUrl = url;
    }


    public String buildUrlPageTitle(String keyWord,String url) {
        setUrl(url+"/search/");
        Element inputElement = GetElementsTags.getElementTagInput(getDoc());
        if (inputElement == null) {
            throw new ParsingSoupException(NOT_FOUND_INPUT);
        }
        return getUrl() + "?" + inputElement.attr("name") + "=" + keyWord;
    }
}
