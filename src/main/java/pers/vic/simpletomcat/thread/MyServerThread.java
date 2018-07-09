package pers.vic.simpletomcat.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import pers.vic.simpletomcat.reveiver.CommandReceiver;
import pers.vic.simpletomcat.http.HttpServer;

import java.net.Socket;


/**
 * Create By Vic Xu on 7/6/2018
 *
 * @author Administrator
 */
@Data
@AllArgsConstructor
public class MyServerThread implements Runnable {

    private CommandReceiver commandReceiver;

    private Socket socket;

    @Override
    public void run() {
        HttpServer httpServer = new HttpServer();
        try {
            httpServer.server(commandReceiver, socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
