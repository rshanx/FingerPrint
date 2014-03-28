package com.Storchak.logic;


import com.Storchak.dao.UsersDao;
import com.Storchak.dto.User;

public class UserService {

    private UsersDao usersDao;

    public UserService(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public User createUser(String name, String sName) {
        User user = new User(name, sName);
        usersDao.createUser(user);
        return user;
    }
}
