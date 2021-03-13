package ru.Senkova.app.impl;

import org.springframework.http.ResponseEntity;
import ru.Senkova.adapter.rest.service.dto.InputParametersFormDto;
import ru.Senkova.adapter.rest.service.dto.SendEmailFormDto;
import ru.Senkova.app.api.ParsingSitesService;
import ru.Senkova.app.impl.monitoring.ParsingSitesServiceImpl;
import ru.Senkova.domain.UserApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ValidateVariable {
    public static final String VALID_TAG_FOR_PAGE_TITLE = ".*title.*";
    public static final String VALID_TAG_FOR_PAGE_TITLE_BY_TEXT = "[пП]о [вВДд][ра][те][ме]е?н?и?";
    public static final String VALID_TAG_FOR_DATE = ".{0,10}[0-9].{0,10}[2[0-9]][0[0-9]][2:][1[0-9]][.?[0-9]].*";
    public static final String VALID_URL_HREF_ALL = "http.*";
    public static final String VALID_URL_HREF_DOMAIN = ".*[.].*";
    public static final String VALID_HTML_IMG =".*<img class=.?logo.*";
    public static final String VALID_HTML_P1 =".*<p class=.?p1.*";
    public static final String VALID_HTML_P2 =".*<p class=.?p2.*";
    public static final String VALID_HTML_P3 =".*<p class=.?p3.*";

/*    public static void main(String[] args) {

        ParsingSitesService parsingSitesService = new ParsingSitesServiceImpl();

        InputParametersFormDto dto=new InputParametersFormDto();
        dto.setKeyWord("Москва");
        dto.setLogin("admin");
        dto.setUrls(new HashSet<String>(Arrays.asList("https://habr.com/","ria.ru")));

        if(!parsingSitesService.validateUrls(dto.getUrls()))
            System.out.println("Неправильно введены ссылки");
        UserApp userApp=parsingSitesService.findUserAppByLogin(dto.getLogin());
        if(userApp.isConfirmationMail()){
            parsingSitesService.saveHyperlinksUsers(userApp, dto.getUrls(), dto.getKeyWord());
            parsingSitesService.saveLink(dto.getKeyWord(),userApp);
            parsingSitesService.startMonitoring(userApp,dto.getKeyWord());
            System.out.println("Мониторинг начался");
        }
        else{
           System.out.println("Мониторинг закончился");
        }
   }*/
}
