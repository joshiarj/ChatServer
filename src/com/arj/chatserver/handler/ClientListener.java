/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arj.chatserver.handler;

import com.arj.chatserver.dao.UserDAO;
import com.arj.chatserver.dao.impl.UserDAOImpl;
import com.arj.chatserver.entity.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Zeppelin
 */
public class ClientListener extends Thread {

    private Socket socket;
    private Client client;
    private PrintStream ps;
    private ClientHandler handler;
    private BufferedReader reader;
    private UserDAO userDAO = new UserDAOImpl();

    public ClientListener(Socket socket, ClientHandler handler) throws IOException {
        this.socket = socket;
        this.handler = handler;
        ps = new PrintStream(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            login();
            while (!isInterrupted()) {
                ps.print("> ");
                String line = reader.readLine();
                String[] tokens = line.split("::");
                if (tokens.length == 1) {
                    if(tokens[0].equalsIgnoreCase("list")) {
                        ps.println(handler.getAll());
                    } else if(tokens[0].equalsIgnoreCase("exit")) {
                        ps.println("Exiting from chatserver...");
                        break;
                    }
                    String msg = client.getUserName() + " says >> " + tokens[tokens.length - 1];
                    broadcastMessage(client, msg);
                } else {
                    String command = tokens[0];
                    String receiver = tokens[1];
                    if (command.equalsIgnoreCase("pm")) {
                        if (tokens.length > 2) {
                            String pm = client.getUserName() + " has PM'd you >> " + tokens[tokens.length - 1];
                            privateMessage(handler.getByUserName(receiver), pm);
                            //broadcastMessage(client.getUserName().equalsIgnoreCase(tokens[1]), msg);
                        }
                    } 
                    else {
                        ps.println("Invalid command.");
                        break;
                    }
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public boolean login() throws IOException {
        while (true) {
            ps.println("Enter your name:");
            String name = reader.readLine();
            ps.println("Enter your password:");
            String password = reader.readLine();

            User user = userDAO.login(name, password);
            if (user == null) {
                ps.println("Invalid login");
            } else {
                ps.println("You have successfully logged in.");
                client = new Client(name, socket);
                handler.addClient(client);
                return true;
            }
        }
    }

    private void broadcastMessage(Client client, String message) throws IOException {
        for (Client c : handler.getAll()) {
            if (!c.equals(client)) {
                PrintStream out = new PrintStream(c.getSocket().getOutputStream());
                out.println(message);
            }
        }
    }

    private void privateMessage(Client receiver, String pm) throws IOException {
        PrintStream out = new PrintStream(receiver.getSocket().getOutputStream());
        out.println(pm);
    }

}
