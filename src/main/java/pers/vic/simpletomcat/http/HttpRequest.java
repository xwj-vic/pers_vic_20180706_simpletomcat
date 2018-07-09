package pers.vic.simpletomcat.http;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import pers.vic.simpletomcat.entity.ConnectInfo;
import pers.vic.simpletomcat.thread.MyServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Create By Vic Xu on 7/9/2018
 */
public class HttpRequest {

    private ConnectInfo connectInfo;

    public HttpRequest(ConnectInfo connectInfo) {
        this.connectInfo = connectInfo;
    }

    public void connect() throws IOException {
        ExecutorService pool = initThreadPool();
        ServerSocket server = new ServerSocket(connectInfo.getPort());
        while (true) {
            Socket socket = server.accept();
            pool.execute(new MyServerThread(connectInfo.getReceiver(), socket));
        }
//        pool.shutdown();
    }

    private ExecutorService initThreadPool() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("http-pool-%d").build();
        return new ThreadPoolExecutor(20, 200, 5,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
    }
}
