package com.lilacode.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

//@Entity
//@Table(name = "USER")
public class User {
    public User(int id,String nickname) {
        this.nickname = nickname;
        this.id = id;
    }

    //unique
    String nickname;
    int id;

//    @OneToMany(mappedBy="user")
//    List<Message> inbox;
//    @OneToMany(mappedBy="user")
//    List<Message> outbox;

//    public List<Message> getInbox() {
//        return inbox;
//    }
//
//    public void setInbox(List<Message> inbox) {
//        this.inbox = inbox;
//    }
//
//    public List<Message> getOutbox() {
//        return outbox;
//    }
//
//    public void setOutbox(List<Message> outbox) {
//        this.outbox = outbox;
//    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
