package com.lilacode.service;

import com.lilacode.dao.UserDao;
import com.lilacode.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User createUser(String nickname) {
        User user = new User(-1, nickname);
        Optional<Integer> userID = userDao.create(user);
        user.setId(userID.get());
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userDao.getAll();
    }

    @Override
    public User getUser(int userId) {
        return userDao.get(userId).get();
    }
}
