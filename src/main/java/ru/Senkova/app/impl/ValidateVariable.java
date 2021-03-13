package ru.Senkova.app.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import ru.Senkova.adapter.email.sendMessage.EmailSender;
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
    public static final String VALID_TAG_FOR_DATE = ".{0,8}[0-9]?[0-9]:[0-9][0-9].{0,8}";
    public static final String VALID_URL_HREF_ALL = "http.*";
    public static final String VALID_URL_HREF_DOMAIN = ".*[.].*";
    public static final String VALID_HTML_IMG =".*<img class=.?logo.*";
    public static final String VALID_HTML_P1 =".*<p class=.?p1.*";
    public static final String VALID_HTML_P2 =".*<p class=.?p2.*";
    public static final String VALID_HTML_P3 =".*<p class=.?p3.*";

}
