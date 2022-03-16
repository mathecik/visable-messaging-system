package com.lilacode.service;

import com.lilacode.dao.MessageDao;
import com.lilacode.dao.UserDao;
import com.lilacode.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageDao messageDao;

    @Override
    public String sendMessage(int from, int to, String content) {
        if(from!=to) {
            Message message = new Message(-1, content, from, to);
            int messageId = messageDao.create(message).get();
            if(messageId>0)
                return "Message has been sent successfully";
            else
                return "Message cannot be created...";
        }
        else {
            return "You cannot send a message to yourself!";
        }
    }

    @Override
    public List<Message> getInbox(int userId) {
        return messageDao.findByToId(userId);
    }

    @Override
    public List<Message> getOutbox(int userId) {
        return messageDao.findByFromId(userId);
    }

    @Override
    public String deleteMessage(int messageId) {
        Message message = messageDao.get(messageId).get();
        if(message!=null){
            messageDao.delete(message);
            return "The message is deleted successfully";
        }

        else return "The message is not found";
    }
}
