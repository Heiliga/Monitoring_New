package ru.Senkova.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "HYPERlINKS_USER_APP")
public class HyperlinksUsers implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_hyperlinks_user_app_id")
    @SequenceGenerator(name = "pk_hyperlinks_user_app_id", sequenceName = "sq_hyperlinks_user_app_id", allocationSize = 1)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idUserApp")
    private UserApp userApp;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name="idHyperlinks")
    private Hyperlinks hyperlinks;

    private String keyWord;

    private String linkArticle;
    private String linkTitles;
    //private boolean startMonitoring;

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


    public String getLinkTitles() {
        return linkTitles;
    }

    public void setLinkTitles(String linkTitles) {
        this.linkTitles = linkTitles;
    }

/*    public boolean isStartMonitoring() {
        return startMonitoring;
    }

    public void setStartMonitoring(boolean startMonitoring) {
        this.startMonitoring = startMonitoring;
    }*/
}
