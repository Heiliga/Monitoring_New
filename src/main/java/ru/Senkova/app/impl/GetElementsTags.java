package ru.Senkova.app.impl;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Senkova.exception.ParsingSoupException;

import java.util.*;

import static ru.Senkova.exception.ResponseCodeException.INT_CODE_5_NOT_FIND_TAG_FOR_ARTICLE;

public class GetElementsTags {

    public static final Element getElementsByText(Document doc, String validText) {
        Elements rightTags =doc.select("[href*=date]" + ":matches("+validText+")");
         if(rightTags.size()!=0)
                return rightTags.last();
        return null;
    }

    public static final Element getElementsTagInput(Document doc) {
        return doc.select("input[name]").first();
    }

    public static final Elements getElementsWithTitles(Document doc, String valid) {
        Elements tagsA = getElementsTagA(doc.select("a[href]"),valid);
        if (tagsA.size()==0) {
            tagsA = getElementsAInСontainerTag(doc.select(":has(a[href])"),valid);
        }
        return tagsA;
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

