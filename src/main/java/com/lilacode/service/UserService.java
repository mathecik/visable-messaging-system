package com.lilacode.service;

import com.lilacode.entities.User;

import java.util.List;

public interface UserService {

    User createUser(String nickname);
    List<User> getAllUsers();
    User getUser(int userId);
}
