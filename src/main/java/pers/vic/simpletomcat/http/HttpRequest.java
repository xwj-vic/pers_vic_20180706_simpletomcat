package pers.vic.simpletomcat.http;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import pers.vic.simpletomcat.reveiver.BooksCommandReceiver;
import pers.vic.simpletomcat.thread.MyServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Create By Vic Xu on 7/9/2018
 */
public class HttpRequest {
    private int port;

    public HttpRequest(int port) {
        this.port = port;
    }

    public void connect() throws IOException {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("http-pool-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(20, 200, 5,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        ServerSocket server = new ServerSocket(port);
        while (true) {
            Socket socket = server.accept();
            pool.execute(new MyServerThread(new BooksCommandReceiver(), socket));

        }
    }
}
