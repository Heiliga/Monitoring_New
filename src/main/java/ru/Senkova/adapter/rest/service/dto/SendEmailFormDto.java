package ru.Senkova.adapter.rest.service.dto;

import com.sun.xml.bind.v2.TODO;

public class SendEmailFormDto {
    private String article;
    private String date;
    private String emailUser;
    private String linkSite;
    private String nameSite;
    private String linkArticle;
    private String login;
    private String firstName;
    private String patronymic;
    private String keyWord;

    public void setNameSite(String nameSite) {
        this.nameSite = nameSite;
    }

    public String getNameSite() {
        String linkWithOutHTTP = linkSite.substring(0,linkSite.indexOf("/"));
        linkSite = linkSite.replace(linkWithOutHTTP,"");
        linkSite = linkSite.substring(2);
        if(linkSite.indexOf('/')!=-1)
            return linkSite.substring(0,linkSite.indexOf("/"));
        else return linkSite;

    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }


    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getLinkSite() {
        return linkSite;
    }

    public void setLinkSite(String link) {
        this.linkSite = link;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getLinkArticle() {
        return linkArticle;
    }

    public void setLinkArticle(String linkArticle) {
        this.linkArticle = linkArticle;
    }

    public String getFullName() {
        return  String.format("%s %s", firstName, patronymic);
    }
}
