package com.lilacode.controller;

import com.lilacode.controller.payload.MessagePayload;
import com.lilacode.entities.Message;
import com.lilacode.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    @Resource
    private MessageService messageService;

    @PostMapping(consumes = {"application/json"})
    public void sendMessage(@RequestBody MessagePayload message) {
        messageService.sendMessage(message.getFrom(), message.getTo(), message.getContent());
    }
    @GetMapping(value = "/received")
    public List<Message> getInbox(@RequestParam(value = "user") int userId) {
        return messageService.getInbox(userId);
    }

    @GetMapping(value = "/sent")
    public List<Message> getOutbox(@RequestParam(value = "user") int userId) {
        return messageService.getOutbox(userId);
    }

    @DeleteMapping("/{id}")
    public String deleteMessage(@PathVariable("id") Integer messageId){
        return messageService.deleteMessage(messageId);
    }
}
