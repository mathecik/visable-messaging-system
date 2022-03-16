package com.lilacode.entities;

import javax.persistence.*;

public class Message {
    Integer id;
    String content;
    Integer from;
    Integer to;

    public Message(Integer id, String content, int from, int to) {
        this.id = id;
        this.content = content;
        this.from = from;
        this.to = to;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Message["
                + "id=" + id
                + ", from=" + from
                + ", to=" + to
                + ", content=" + content
                + ']';
    }

}
