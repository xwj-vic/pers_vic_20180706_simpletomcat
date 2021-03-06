package pers.vic.simpletomcat.http;

import org.apache.commons.lang3.StringUtils;
import pers.vic.simpletomcat.command.DeleteRequest;
import pers.vic.simpletomcat.command.GetRequest;
import pers.vic.simpletomcat.command.PostRequest;
import pers.vic.simpletomcat.command.PutRequest;
import pers.vic.simpletomcat.data.BaseData;
import pers.vic.simpletomcat.data.ResourceList;
import pers.vic.simpletomcat.invoker.HttpCommandInvoker;
import pers.vic.simpletomcat.reveiver.CommandReceiver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * Create By Vic Xu on 7/9/2018
 */
public class HttpResponse {

    private Socket socket;

    private CommandReceiver commandReceiver;

    private String[] content;

    public HttpResponse(Socket socket, CommandReceiver commandReceiver, String[] content) {
        this.socket = socket;
        this.commandReceiver = commandReceiver;
        this.content = content;
    }

    public void response() {
        HttpKeyPointUtil keyPointUtil = new HttpKeyPointUtil();
        Map<String, String> map = keyPointUtil.getKeyPoint(content);
        if (ResourceList.hasSuffix(map.get("url"))) {
            fileHandle(map.get("url"), keyPointUtil);
        } else if (ResourceList.urlAccess(map.get("url")) && !map.get("url").contains(".")) {
            requestHandle(map, commandReceiver);
        } else {
            doExceptionRequest("404");
        }
    }

    private void requestHandle(Map<String, String> map, CommandReceiver receiver) {
        try {
            String result = "";
            switch (map.get("request")) {
                case "GET":
                    result = new HttpCommandInvoker(new GetRequest(receiver, map)).action();
                    break;
                case "POST":
                    result = new HttpCommandInvoker(new PostRequest(receiver, map)).action();
                    break;
                case "PUT":
                    result = new HttpCommandInvoker(new PutRequest(receiver, map)).action();
                    break;
                case "DELETE":
                    result = new HttpCommandInvoker(new DeleteRequest(receiver, map)).action();
                    break;
                default:
                    break;
            }
            doRequestUtil(result, map.get("host"));
        } catch (Exception e) {
            doExceptionRequest("500");
            e.printStackTrace();
        }
    }

    private void fileHandle(String path, HttpKeyPointUtil keyPointUtil) {
        path = keyPointUtil.getPath(path);
        String type = keyPointUtil.getContentType(path);
        File file = new File(path);
        if (file.exists()) {
            doRequestFile(file, type);
        } else if (StringUtils.isNotBlank(ResourceList.getRedirectUrl(path))) {
            doRedirct(ResourceList.getRedirectUrl(path));
        } else {
            doExceptionRequest("404");
        }
    }

    private void doRequestUtil(String json, String host) {
        PrintStream print = null;
        try {
            long length = json.length();
            print = new PrintStream(socket.getOutputStream(), true);
            print.println("HTTP/1.1 200 OK");
            print.println("Cache-Control: no-cache");
            print.println("Host:" + host);
            print.println("accept:*/*");
            print.println("Connection: keep-alive");
            print.println("Content-Type:application/json;charset=utf-8");
            print.println("Accept-Language: en-us,zh-cn;q=0.5");
            print.println("Content-Length:" + length);
            print.println();
            print.write(json.getBytes());
            print.flush();
        } catch (IOException e) {
            e.printStackTrace();
            print.close();
        }
    }

    private void doRequestFile(File file, String type) {
        FileInputStream fis = null;
        PrintStream print = null;
        try {
            long length = file.length();
            print = new PrintStream(socket.getOutputStream(), true);
            print.println("HTTP/1.1 200 OK");
            print.println("Content-Type:" + type + ";charset=utf-8");
            print.println("Accept-Language: en-us,zh-cn;q=0.5");
            print.println("Content-Length: " + length);
            print.println();
            byte[] bytes = new byte[(int) length * 2];
            fis = new FileInputStream(file);
            fis.read(bytes);
            print.write(bytes);
            print.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                print.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doRedirct(String url) {
        PrintStream print = null;
        try {
            print = new PrintStream(socket.getOutputStream(), true);
            print.println("HTTP/1.1 302 FOUND");
            print.println("Date:" + new Date());
            print.println("Server:Redirector 1.1");
            print.println("Location:" + url);
            print.println();
            print.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(print).close();
        }
    }

    private void doExceptionRequest(String type) {
        PrintStream print = null;
        FileInputStream fis = null;
        File file = null;
        try {
            print = new PrintStream(socket.getOutputStream(), true);
            if ("404".equalsIgnoreCase(type)) {
                file = new File(BaseData.HTML_PATH + "/404.html");
                print.println("HTTP/1.1 404 Not found");
            } else if ("500".equalsIgnoreCase(type)) {
                print.println("HTTP/1.1 500 Error");
                file = new File(BaseData.HTML_PATH + "/500.html");
            }
            print.println();
            byte[] bytes = new byte[(int) (file.length() * 2)];
            fis = new FileInputStream(file);
            fis.read(bytes);
            print.write(bytes);
            print.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                print.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
