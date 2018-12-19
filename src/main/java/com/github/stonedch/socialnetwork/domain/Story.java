package com.github.stonedch.socialnetwork.domain;

import javax.persistence.*;

@Entity
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account author;
    private String content;
    private String tag;

    public Story() {
    }

    public Story(Account account) {
        this.author = account;
    }

    public Story(String content, String tag, Account account) {
        this.content = content;
        this.tag = tag;
        this.author = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account account) {
        this.author = account;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
