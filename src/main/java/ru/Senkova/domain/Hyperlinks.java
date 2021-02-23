package ru.Senkova.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "HYPERLINKS", schema = "PUBLIC")
public class Hyperlinks {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "link_id_generator")
    @SequenceGenerator(name = "link_id_generator", sequenceName = "sq_link_id", allocationSize = 1)
    private Long id;

    @NaturalId
    private String link;

    public Hyperlinks(String link) {
        this.link=link;
    }

    public Hyperlinks() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
