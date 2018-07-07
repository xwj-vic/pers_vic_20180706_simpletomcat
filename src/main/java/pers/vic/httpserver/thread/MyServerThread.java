package pers.vic.httpserver.thread;

import pers.vic.httpserver.http.HttpServer;

import java.io.IOException;


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
