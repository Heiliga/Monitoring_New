package ru.Senkova.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@EqualsAndHashCode
@ToString
public class HyperlinksUsersPK implements Serializable {
    static final long serialVersionUID = 1L;

    private Hyperlinks hyperlinks;

    private UserApp userApp;

    private String keyWord;

    public HyperlinksUsersPK() {
    }

    public HyperlinksUsersPK(Hyperlinks hyperlinks, UserApp userApp, String keyWord) {
        this.hyperlinks = hyperlinks;
        this.userApp = userApp;
        this.keyWord = keyWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HyperlinksUsersPK that = (HyperlinksUsersPK) o;
        return Objects.equals(hyperlinks, that.hyperlinks) && Objects.equals(userApp, that.userApp) && Objects.equals(keyWord, that.keyWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hyperlinks, userApp, keyWord);
    }

}
