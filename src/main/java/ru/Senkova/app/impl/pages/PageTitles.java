package ru.Senkova.app.impl.pages;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Senkova.app.impl.GetElementsTags;

import static ru.Senkova.app.impl.ValidateVariable.*;

public class PageTitles extends ObjectDocument {

    private Element linkLastElement;

    public PageTitles(PageSearch pageSearch, String keyWord){
        super(pageSearch.buildUrlPageTitle(keyWord));
    }

    @Override
    public final String getUrl() {
        Element sortTime = GetElementsTags.getElementsByText(getDoc(), VALID_TAG_FOR_PAGE_TITLE_BY_TEXT);
        if (sortTime != null)
            setUrl(sortTime.attr("href"));
        return super.getUrl();
    }

    public final Element getLinkLastElement() {
        Elements elementTagA = GetElementsTags.getElementsWithTitles(getDoc(), VALID_TAG_FOR_PAGE_TITLE);
        if (elementTagA.size()==0)
            return null;
        return elementTagA.first();
    }

}
