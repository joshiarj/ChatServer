/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arj.chatserver.dao.impl;

import com.arj.chatserver.dao.UserDAO;
import com.arj.chatserver.entity.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zeppelin
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "John", "john", "john@beatles.com", true));
        userList.add(new User(2, "Paul", "paul", "paul@beatles.com", true));
        userList.add(new User(3, "Ringo", "ringo", "ringo@starr.son", true));
        userList.add(new User(4, "George", "george", "george@harri.son", true));
        return userList;
    }

    @Override
    public User login(String userName, String password) {
        for (User u : getAll()) {
            if (u.getName().equalsIgnoreCase(userName) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

}
