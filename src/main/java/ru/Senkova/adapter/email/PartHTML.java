package ru.Senkova.adapter.email;

import ru.Senkova.adapter.rest.service.dto.SendEmailFormDto;

import java.util.Scanner;

import static ru.Senkova.app.impl.ValidateVariable.*;


public class PartHTML {

    public static final String getPartCodeHtml(SendEmailFormDto dto, Scanner inFile) {
        StringBuilder builder = new StringBuilder();
        while (inFile.hasNext()) {
            String line = inFile.nextLine();
            if (line.matches(VALID_HTML_IMG))
                builder.append("<img src='cid:some_image_id'>");
            else if (line.matches(VALID_HTML_P1))
                builder.append("<p class ='block_center' align='justify'>" + dto.getFullName() + ", на сайте <a href='" + dto.getLinkSite() + "'>"
                    + dto.getNameSite() + "</a> совсем недавно была опубликована новая запись по интересующему вас запросу '"
                    + dto.getKeyWord() + "', приятного прочтения!" + "</p>");
            else if (line.matches(VALID_HTML_P2))
                builder.append("<p class ='block_center' align='justify'> Дата публикации:  <a class='date color_coffee'>" + dto.getDate() + "</a></p>" +
                    "<p class ='block_center' align='justify'> Название статьи:  " + "<a href='"
                    + dto.getLinkArticle() + "'>" + "<h3 class='article'>" + dto.getArticle() + "</h3></a>" + "</p>"
                );
            else if (line.matches(VALID_HTML_P3))
                builder.append("<p class ='block_center' align='justify'> Перейти на статью можно по ссылке " + dto.getLinkArticle() + "</p>");
                else builder.append(line);
        }
        inFile.close();
        return builder.toString();
    }

}
