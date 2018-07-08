package pers.vic.simpletomcat.thread;

import pers.vic.simpletomcat.http.HttpServer;


/**
 * Create By Vic Xu on 7/6/2018
 * @author Administrator
 */
public class MyServerThread implements Runnable {
    @Override
    public void run() {
        HttpServer httpServer = new HttpServer();
        try {
            httpServer.server();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
