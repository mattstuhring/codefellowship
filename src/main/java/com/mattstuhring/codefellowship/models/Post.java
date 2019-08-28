package com.mattstuhring.codefellowship.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String body;
    String createdAt;

    @ManyToOne
    ApplicationUser owner;

    public Post() {}

    public Post(String body, String createdAt, ApplicationUser owner) {
        this.body = body;
        this.createdAt = createdAt;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
