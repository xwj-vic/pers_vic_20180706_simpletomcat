package pers.vic.simpletomcat.thread;

import pers.vic.simpletomcat.reveiver.CommandReceiver;
import pers.vic.simpletomcat.http.HttpServer;

import java.net.Socket;


/**
 * Create By Vic Xu on 7/6/2018
 *
 * @author Administrator
 */

public class MyServerThread implements Runnable {

    private CommandReceiver commandReceiver;

    private Socket socket;

    public MyServerThread(CommandReceiver commandReceiver, Socket socket) {
        this.commandReceiver = commandReceiver;
        this.socket = socket;
    }

    @Override
    public void run() {
        HttpServer httpServer = new HttpServer();
        try {
            httpServer.server(commandReceiver, socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandReceiver getCommandReceiver() {
        return commandReceiver;
    }

    public void setCommandReceiver(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
