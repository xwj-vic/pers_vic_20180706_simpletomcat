package pers.vic.simpletomcat.thread;

import pers.vic.simpletomcat.http.HttpKeyPointUtil;
import pers.vic.simpletomcat.http.HttpResponse;
import pers.vic.simpletomcat.reveiver.CommandReceiver;

import java.io.InputStream;
import java.net.Socket;
import java.util.Objects;


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
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] buf = new byte[2048 * 2048];
            int len = inputStream.read(buf);
            String s = null;
            if (len != -1) {
                s = new String(buf, 0, len);
            }
            String[] content = HttpKeyPointUtil.spilts(Objects.requireNonNull(s), "\r\n");
            HttpResponse httpResponse = new HttpResponse(socket, commandReceiver, content);
            httpResponse.response();
            inputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
