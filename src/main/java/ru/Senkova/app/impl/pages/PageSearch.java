package ru.Senkova.app.impl.pages;

import org.jsoup.nodes.Element;
import ru.Senkova.app.impl.GetElementsTags;
import ru.Senkova.exception.ParsingSoupException;

import static ru.Senkova.exception.ResponseCodeException.INT_CODE_8_NOT_FOUND_INPUT;

public class PageSearch extends ObjectDocument {

    public PageSearch(String url) {
        super(url +"/search/");
    }


    public String buildUrlPageTitle(String keyWord){
        Element inputElement = GetElementsTags.getElementsTagInput(getDoc());
        if(inputElement==null)
            throw new ParsingSoupException(INT_CODE_8_NOT_FOUND_INPUT);
        return getUrl()+"?"+inputElement.attr("name")+"="+keyWord;
    }

}
