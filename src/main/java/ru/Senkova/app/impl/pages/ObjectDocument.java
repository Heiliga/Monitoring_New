package ru.Senkova.app.impl.pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.Senkova.exception.ParsingSoupException;

import java.io.IOException;

import static ru.Senkova.exception.ResponseCodeException.INT_CODE_7_NOT_Creation_Document;

public abstract class ObjectDocument {

    private Document doc;
    private String url;

    //Обязательно проделать манипуляции с преобразованием url под каждый класс
    public ObjectDocument(String url) {
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url){
        this.url=url;
    }

    private final boolean getConnectJsoup(){
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
        if(getConnectJsoup() == false)
            throw new ParsingSoupException(INT_CODE_7_NOT_Creation_Document);
        return doc;
    }
    public void setDoc(Document doc){
        this.doc=doc;
    }


}
