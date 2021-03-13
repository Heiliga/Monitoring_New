package ru.Senkova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.Senkova.adapter.hibernate")
@EntityScan(basePackages = "ru.Senkova.domain")
public class Application {
/*    public static void main(String[] args) { //todo
*//*        PageSearch pageSearch = new PageSearch("https://news.rambler.ru/");
        PageTitles pageTitles = new PageTitles(pageSearch.buildUrlPageTitle("Москва"));
        String link = pageTitles.getLinkTitles();
        Element lastElement = pageTitles.getLinkLastElement();
        System.out.printf("%s %s", link, lastElement);*//*

    }
}*/
   public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
