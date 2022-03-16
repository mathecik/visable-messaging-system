package com.lilacode.controller;

import com.lilacode.entities.Message;
import com.lilacode.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    @Resource
    private MessageService messageService;

    @PostMapping("/send")
    public void sendMessage(@RequestParam(value = "from") int from, @RequestParam(value = "to") int to, @RequestParam(value = "content") String content) {
        messageService.sendMessage(from, to, content);
    }
    @GetMapping(value = "/inbox")
    public List<Message> getInbox(@RequestParam(value = "user") int userId) {
        return messageService.getInbox(userId);
    }

    @GetMapping(value="/outbox")
    public List<Message> getOutbox(@RequestParam(value = "user") int userId) {
        return messageService.getOutbox(userId);
    }

    @DeleteMapping(value="/delete")
    public String deleteMessage(@RequestParam(value = "id") int messageId){
        return messageService.deleteMessage(messageId);
    }
}
