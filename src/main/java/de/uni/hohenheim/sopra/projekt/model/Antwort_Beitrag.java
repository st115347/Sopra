package de.uni.hohenheim.sopra.projekt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by Tabea on 12.06.16.
 */
@Entity
public class Antwort_Beitrag {

    @Id
            @GeneratedValue
    Integer id;

    @Column(name = "author")
    String to;

    @Column(name = "authorfrom")
    String author;

    @Column(name = "content")
    String content;

    @Column(name = "topic")
    String topic;

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getTo() {
        return to;
    }

    public String getTopic() {
        return topic;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

