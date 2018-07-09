package pers.vic.simpletomcat.http;

import org.apache.commons.lang3.StringUtils;
import pers.vic.simpletomcat.HttpCommandInvoker;
import pers.vic.simpletomcat.command.*;
import pers.vic.simpletomcat.data.ResourceList;
import pers.vic.simpletomcat.reveiver.CommandReceiver;

import java.io.*;
import java.net.ServerSocket;
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
        //提取特征信息
        Map<String, String> map = keyPointUtil.getKeyPoint(content);
        HttpRequest httpRequest = new HttpRequest(socket);
        if (ResourceList.hasSuffix(map.get("url"))) {
            fileRender(map.get("url"), httpRequest, keyPointUtil);
        } else if (ResourceList.urlAccess(map.get("url")) && !map.get("url").contains(".")) {
            requestHandle(map, httpRequest, commandReceiver);
        } else {
            httpRequest.doExceptionRequest("404");
        }
        inputStream.close();
        socket.close();
    }

    private void requestHandle(Map<String, String> map, HttpRequest httpRequest, CommandReceiver receiver) {
        try {
            String result;
            switch (map.get("request")) {
                case "GET":
                    result = new HttpCommandInvoker(new GetRequest(receiver, map)).action();
                    httpRequest.doRequestUtil(result, map.get("host"));
                    break;
                case "POST":
                    result = new HttpCommandInvoker(new PostRequest(receiver, map)).action();
                    httpRequest.doRequestUtil(result, map.get("host"));
                    break;
                case "PUT":
                    result = new HttpCommandInvoker(new PutRequest(receiver, map)).action();
                    httpRequest.doRequestUtil(result, map.get("host"));
                    break;
                case "DELETE":
                    result = new HttpCommandInvoker(new DeleteRequest(receiver, map)).action();
                    httpRequest.doRequestUtil(result, map.get("host"));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            httpRequest.doExceptionRequest("500");
            e.printStackTrace();
        }
    }

    private void fileRender(String path, HttpRequest httpRequest, HttpKeyPointUtil keyPointUtil) {
        path = keyPointUtil.getPath(path);
        String type = keyPointUtil.getContentType(path);
        File file = new File(path);
        if (file.exists()) {
            httpRequest.doRequestFile(file, type);
        } else if (StringUtils.isNotBlank(ResourceList.getRedirectUrl(path))) {
            httpRequest.doRedirct(ResourceList.getRedirectUrl(path));
        } else {
            httpRequest.doExceptionRequest("404");
        }
    }
}
