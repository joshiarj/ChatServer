/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arj.chatserver;

import com.arj.chatserver.handler.ClientHandler;
import com.arj.chatserver.handler.ClientListener;
import com.arj.chatserver.util.FileHelper;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Zeppelin
 */
public class SaveToLogFile {

    private String message;
    private Socket socket;
    private ClientListener listener;
    private ClientHandler handler;
    private String[] tokens;

    public SaveToLogFile() {
    }

    public SaveToLogFile(String message, Socket socket, ClientListener listener, ClientHandler handler) {
        this.message = message;
        this.socket = socket;
        this.listener = listener;
        this.handler = handler;
    }

    public void saveToFile() throws IOException {
        String path = "C:/Users/Zeppelin/Documents/NetBeansProjects/ChatServer/src/com/arj/chatserver/log/";
        String logFileName = path + "chatlog.log";
        StringBuilder content = new StringBuilder();
        while (socket.isConnected()) {
            content.append(listener.toString()).append("\r\n");
        }
//        String logText = content.toString();
//        FileWriter writer = new FileWriter(logFileName);
//        writer.write(content.toString());
//        writer.close();
        //FileHelper helper = new FileHelper();
        FileHelper.write(logFileName, content.toString());
    }

}
