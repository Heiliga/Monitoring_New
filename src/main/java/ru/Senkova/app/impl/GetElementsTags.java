package ru.Senkova.app.impl;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Senkova.app.impl.monitoring.pages.PageSearch;

import static ru.Senkova.app.impl.ValidateVariable.VALID_URL_HREF_ALL;

public class GetElementsTags {

    public static final Element getElementsByText(Document doc, String validText) {
        Elements rightTags =doc.select("[href*=date]" + ":matches("+validText+")");
         if(rightTags.size()!=0)
                return rightTags.last();
        return null;
    }

    public static final Element getElementTagInput(Document doc) {
        return doc.select("input[name]").first();
    }

    public static final Elements getElementsWithTitles(Document doc, String valid, String startUrl) {
        Elements tagsA = getElementsTagA(doc.select("a[href]"),valid);
        if (tagsA.size()==0) {
            tagsA = getElementsAInСontainerTag(doc.select(":has(a[href])"),valid);
        }
        if(!tagsA.attr("href").matches(VALID_URL_HREF_ALL))
            tagsA.attr("href", startUrl + tagsA.attr("href"));
        return tagsA;
    }

    public static final Element getElementsForDate(Document doc, String validValue){
        Elements rightTags = doc.select(":matchesOwn("+validValue+")");
        Element rightTag = doc.select(":matchesOwn("+validValue+")").first();
        return rightTag;
    }

    public static final Element getElementsForTitle(Document doc){
        Elements rightTags = doc.select("h1");
        if(rightTags.text().length()>0)
            return rightTags.first();
        else return null;
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private static final Elements getElementsTagA(Elements tagsA, String valid) {
        Elements tagsAWithCheck = new Elements();
        for (Element element : tagsA) {
            Attributes attributes = element.attributes();
            for (Attribute attribute : attributes)
                if (attribute.getValue().matches(valid)) {
                    tagsAWithCheck.add(element);
                    break;
                }
        }
        return tagsAWithCheck;

    }
    private static final Elements getElementsAInСontainerTag(Elements tagContainerA, String valid) {
        Elements tagsAWithCheck = getElementsTagA(tagContainerA,valid);
        for(int i=0; i < tagsAWithCheck.size(); i++){
            tagsAWithCheck.set(i,tagsAWithCheck.get(i).getElementsByTag("a").first());
        }
        return tagsAWithCheck;
    }

}

