package ru.Senkova.app.impl.monitoring.pages;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Senkova.app.impl.GetElementsTags;

import static ru.Senkova.app.impl.ValidateVariable.VALID_TAG_FOR_PAGE_TITLE;
import static ru.Senkova.app.impl.ValidateVariable.VALID_TAG_FOR_PAGE_TITLE_BY_TEXT;

public class PageTitles extends ObjectDocument {

    private boolean flag = false;
    private String linkLastElement;

    public PageTitles(String url) {
        super(url);
    }

    @Override
    public final String getUrl() {
        Element sortTime = GetElementsTags.getElementsByText(getDoc(), VALID_TAG_FOR_PAGE_TITLE_BY_TEXT);
        if (sortTime != null) {
            setUrl(sortTime.attr("href"));
        }
        return super.getUrl();
    }

    public final Elements getElementsArticles(String url) {

        return GetElementsTags.getElementsWithTitles(getDoc(), VALID_TAG_FOR_PAGE_TITLE,url);
    }

    public final String getLinkLastElement(String url) {
        if (!flag) {
            Elements elementTagA = getElementsArticles(url);
            flag = true;
            if (elementTagA.size() == 0) {
                return null;
            }
            return elementTagA.first().attr("href");
        } else {
            return linkLastElement;
        }
    }
}
