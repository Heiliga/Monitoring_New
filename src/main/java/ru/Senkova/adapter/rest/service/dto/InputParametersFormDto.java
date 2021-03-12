package ru.Senkova.adapter.rest.service.dto;

import java.util.Set;

/**
 * todo Document type InputParametersFormDto
 */
public class InputParametersFormDto {

    private String login;
    private String keyWord;
    private Set<String> urls;

    public String getKeyWord() {
        return keyWord.trim();
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

    public String getLogin() {
        return login.trim();
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
