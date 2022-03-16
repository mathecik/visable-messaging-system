package com.lilacode.dao;

import com.lilacode.entities.Message;
import java.util.List;

public interface MessageDao extends Dao<Message, Integer>{
     List<Message> findByFromId(Integer from);
     List<Message> findByToId(Integer to);
}
