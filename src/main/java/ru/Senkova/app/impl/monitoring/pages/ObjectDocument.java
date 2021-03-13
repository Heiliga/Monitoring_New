package ru.Senkova.app.impl.monitoring.pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.Senkova.exception.ParsingSoupException;

import java.io.IOException;

import static ru.Senkova.exception.ResponseCodeException.NOT_Creation_Document;

public abstract class ObjectDocument {

    private Document doc;
    private String url;

    public ObjectDocument(String url) {
        this.url=url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url){
        this.url=url;
    }

    private final boolean isJsoupConnected(){
        try{
            this.doc = Jsoup.connect(url)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
            return true;
        }
        catch (IOException e){
            return false;
        }
    }

    public Document getDoc(){
        if(!isJsoupConnected())
            throw new ParsingSoupException(NOT_Creation_Document);
        return doc;
    }
    public void setDoc(Document doc){
        this.doc=doc;
    }

}
