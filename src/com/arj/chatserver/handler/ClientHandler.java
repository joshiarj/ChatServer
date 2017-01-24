/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arj.chatserver.handler;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zeppelin
 */
public class ClientHandler {

    private List<Client> clients = new ArrayList<>();

    public void addClient(Client c) {
        clients.add(c);
    }

    public boolean removeClient(Client c) {
        return clients.remove(c);
    }

    public List<Client> getAll() {
        return clients;
    }

    public Client getByUserName(String userName) {
        for (Client c : clients) {
            if (c.getUserName().equalsIgnoreCase(userName)) {
                return c;
            }
        }
        return null;
    }

    public Client getBySocket(Socket socket) {
        for (Client c : clients) {
            if (c.getSocket().equals(socket)) {
                return c;
            }
        }
        return null;
    }

}
