/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arj.chatserver.dao;

import com.arj.chatserver.entity.User;
import java.util.List;

/**
 *
 * @author Zeppelin
 */
public interface UserDAO {
    List<User> getAll();
    User login(String userName, String password);
}
