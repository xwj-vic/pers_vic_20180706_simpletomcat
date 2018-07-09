package pers.vic.simpletomcat.http;

import pers.vic.simpletomcat.data.ResourceList;
import pers.vic.simpletomcat.reveiver.CommandReceiver;

import java.io.*;
import java.net.Socket;
import java.util.Map;

/**
 * Create By Vic Xu on 7/6/2018
 *
 * @author Administrator
 */
public class HttpServer {

    public void server(CommandReceiver commandReceiver, Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] buf = new byte[2048 * 2048];
        int len = inputStream.read(buf);
        String s = null;
        if (len != -1) {
            s = new String(buf, 0, len);
        }
        String[] content = HttpKeyPointUtil.spilts(s, "\r\n");
        HttpKeyPointUtil keyPointUtil = new HttpKeyPointUtil();
        Map<String, String> map = keyPointUtil.getKeyPoint(content);
        HttpResponseBody httpRequest = new HttpResponseBody(socket);
        HttpResponse httpResponse = new HttpResponse();
        if (ResourceList.hasSuffix(map.get("url"))) {
            httpResponse.fileHandle(map.get("url"), httpRequest, keyPointUtil);
        } else if (ResourceList.urlAccess(map.get("url")) && !map.get("url").contains(".")) {
            httpResponse.requestHandle(map, httpRequest, commandReceiver);
        } else {
            httpRequest.doExceptionRequest("404");
        }
        inputStream.close();
        socket.close();
    }
}
