package ru.Senkova.app.impl;

public class ValidateVariable {
    public static final String VALID_TAG_FOR_PAGE_TITLE = ".*title.*";
    public static final String VALID_TAG_FOR_PAGE_TITLE_BY_TEXT = "[пП]о [вВДд][ра][те][ме]е?н?и?";
    public static final String VALID_TAG_FOR_DATE = ".{0,10}[0-9].{0,10}[2[0-9]][0[0-9]][2:][1[0-9]][.?[0-9]].*";
    public static final String VALID_URL_HREF_ALL = "http.*";
    public static final String VALID_URL_HREF_DOMAIN = ".*+.+.?";
    public static final String VALID_HTML_IMG =".*<img class=.?logo.*";
    public static final String VALID_HTML_P1 =".*<p class=.?p1.*";
    public static final String VALID_HTML_P2 =".*<p class=.?p2.*";
    public static final String VALID_HTML_P3 =".*<p class=.?p3.*";

/*    public static void main(String[] args){
*//*        PageSearch pageSearch = new PageSearch("https://www.vesti.ru");
        PageTitles pageTitles=new PageTitles(pageSearch,"Москва");
        PageTitleAndDate titleAndDate= new PageTitleAndDate(pageTitles);
        String time = titleAndDate.getTime();
        System.out.println(time);*//*
        EmailSender emailSender=new EmailSender();
        SendEmailFromDto dto = new SendEmailFromDto();
        dto.setArticle("Москва");
        dto.setDate("в 15:15");
        dto.setEmailUser("Olgary58@mail.ru");
        dto.setLinkArticle("https://ria.ru/20210309/erevan-1600511705.html");
        dto.setLogin("Heiliga");
        dto.setLinkSite("https://ria.ru/");
        dto.setArticle("В Ереване полиция пытается взять в кольцо протестующих у парламента");

        emailSender.sendEmail(dto);
    }*/
}
