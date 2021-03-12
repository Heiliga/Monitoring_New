package ru.Senkova.app.impl.monitoring.pages;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Senkova.app.impl.GetElementsTags;

import static ru.Senkova.app.impl.ValidateVariable.*;

public class PageTitles extends ObjectDocument {

    private boolean flag=false;
    private String linkLastElement;
    private Elements elementsArticles;

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

    public final Elements getElementsArticles() {
        return GetElementsTags.getElementsWithTitles(getDoc(), VALID_TAG_FOR_PAGE_TITLE);
    }

    public final String getLinkLastElement() {
        if(!flag) {
            Elements elementTagA = getElementsArticles();
            flag=true;
            if (elementTagA.size() == 0)
                return null;
            return elementTagA.first().attr("href");
        }
        else return linkLastElement;
    }
    public void setLinkLastElement(String lastElement) {
        this.linkLastElement = lastElement;
    }

}
