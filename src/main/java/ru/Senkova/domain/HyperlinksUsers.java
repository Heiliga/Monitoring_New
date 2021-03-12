package ru.Senkova.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "HYPERlINKS_USER_APP")
@IdClass (HyperlinksUsersPK.class)
public class HyperlinksUsers implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idUserApp")
    private UserApp userApp;

    @Id
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name="idHyperlinks")
    private Hyperlinks hyperlinks;

    @Id
    private String keyWord;

    @Transient
    private String linkArticle;

    public HyperlinksUsers() {
    }

    public HyperlinksUsers(Hyperlinks hyperlinks, String keyWord) {
        this.hyperlinks = hyperlinks;
        this.keyWord = keyWord;
    }

    public HyperlinksUsers(UserApp userApp, Hyperlinks hyperlinks, String keyWord) {
        this(hyperlinks,keyWord);
        this.userApp = userApp;
    }


    public UserApp getUserApp() {
        return userApp;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Hyperlinks getHyperlinks() {
        return hyperlinks;
    }

    public void setHyperlinks(Hyperlinks hyperlinks) {
        this.hyperlinks = hyperlinks;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getLinkArticle() {
        return linkArticle;
    }

    public void setLinkArticle(String linkArticle) {
        this.linkArticle = linkArticle;
    }


}
