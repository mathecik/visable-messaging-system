package com.lilacode.service;

import com.lilacode.entities.Message;

import java.util.List;

public interface MessageService {

    String sendMessage(int from, int to, String content);
    List<Message> getInbox(int userId);
    List<Message> getOutbox(int userId);
    String deleteMessage(int messageId);
}
