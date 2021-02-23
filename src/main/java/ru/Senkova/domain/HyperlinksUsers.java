package ru.Senkova.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.NaturalId;
import org.jsoup.select.Elements;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "HYPERlINKS_USERAPP")
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

    @Transient
    private String linkTitles;

    @Transient
    private Elements lastElements;

    @Transient
    private String tagForLastElements;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss yyyy-MM-dd")
    private Date startMonitoring;

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

    public Date getStartMonitoring() {
        return startMonitoring;
    }

    public void setStartMonitoring(Date startMonitoring) {
        this.startMonitoring = startMonitoring;
    }


    public String getLinkTitles() {
        return linkTitles;
    }

    public void setLinkTitles(String linkTitles) {
        this.linkTitles = linkTitles;
    }

    public Elements getLastElements() {
        return lastElements;
    }

    public void setLastElements(Elements lastElements) {
        this.lastElements = lastElements;
    }

    public String getTagForLastElements() {
        return tagForLastElements;
    }

    public void setTagForLastElements(String tagForLastElements) {
        this.tagForLastElements = tagForLastElements;
    }
}
