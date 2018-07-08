package pers.vic.httpserver.http;

import org.apache.commons.lang3.StringUtils;
import pers.vic.httpserver.HttpCommandInvoker;
import pers.vic.httpserver.HttpCommandReceiver;
import pers.vic.httpserver.command.DeleteRequest;
import pers.vic.httpserver.command.GetRequest;
import pers.vic.httpserver.command.PostRequest;
import pers.vic.httpserver.command.PutRequest;
import pers.vic.httpserver.data.ResourceList;
import pers.vic.httpserver.entity.BooksEntity;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Create By Vic Xu on 7/6/2018
 *
 * @author Administrator
 */
public class HttpServer {
    private static final String BASE_PATH = "src/main/resources";
    private static final String HTML_PATH = BASE_PATH + "/html";

    public void server() throws IOException {
        ServerSocket server = new ServerSocket(9999);
        while (true) {
            Socket socket = server.accept();
            InputStream inputStream = socket.getInputStream();
            byte[] buf = new byte[2048 * 2048];
            int len = inputStream.read(buf);
            String s = new String(buf, 0, len);
            String[] content = spilts(s, "\r\n");
            Map<String, String> map = getKeyPoint(content);
            HttpRequest httpRequest = new HttpRequest(socket);
            if (ResourceList.hasSuffix(map.get("url"))) {
                fileRender(map.get("url"), httpRequest);
            } else if (ResourceList.urlAccess(map.get("url")) && !map.get("url").contains(".")) {
                bookHandle(map, httpRequest);
            } else {
                httpRequest.doExceptionRequest("404");
            }
            inputStream.close();
            socket.close();
        }
    }

    private void bookHandle(Map<String, String> map, HttpRequest httpRequest) {
        try {
            HttpCommandReceiver receiver = new HttpCommandReceiver();
            BooksEntity booksEntity = null;
            if (map.containsKey("info")) {
                String[] infos = spilts(map.get("info"), "&");
                Map<String, String> value = new HashMap<>(16);
                for (String s : infos) {
                    String[] item = spilts(s, "=");
                    value.put(item[0], item[1]);
                }
                booksEntity = new BooksEntity(value.get("bookName"), Double.valueOf(value.get("price")), value.get("author"));
                if (value.containsKey("id")) {
                    booksEntity.setId(value.get("id"));
                }
            }
            String result = null;
            switch (map.get("request")) {
                case "GET":
                    String id = null;
                    if (map.containsKey("parm")) {
                        id = spilts(map.get("parm"), "=")[1];
                    }
                    result = new HttpCommandInvoker(new GetRequest(receiver, id)).action();
                    httpRequest.doRequestUtil(result, map.get("host"));
                    break;
                case "POST":
                    result = new HttpCommandInvoker(new PostRequest(receiver, booksEntity)).action();
                    httpRequest.doRequestUtil(result, map.get("host"));
                    break;
                case "PUT":
                    result = new HttpCommandInvoker(new PutRequest(receiver, booksEntity)).action();
                    httpRequest.doRequestUtil(result, map.get("host"));
                    break;
                case "DELETE":
                    result = new HttpCommandInvoker(new DeleteRequest(receiver, map.get("parm"))).action();
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

    private void fileRender(String path, HttpRequest httpRequest) {
        String allPath = getPath(path);
        String type = getContentType(allPath);
        File file = new File(allPath);
        if (file.exists()) {
            httpRequest.doRequestFile(file, type);
        } else if (StringUtils.isNotBlank(ResourceList.getRedirectUrl(path))) {
            httpRequest.doRedirct(ResourceList.getRedirectUrl(path));
        } else {
            httpRequest.doExceptionRequest("404");
        }
    }

    private String[] spilts(String s, String reg) {
        return s.split(reg);
    }

    /**
     * 提取特征信息
     *
     * @param s
     * @return
     */
    private Map<String, String> getKeyPoint(String[] s) {
        Map<String, String> map = new HashMap<>(16);
        String[] firstLine = spilts(s[0], " ");
        map.put("request", firstLine[0]);
        map.put("url", firstLine[1]);
        if (firstLine[0].equalsIgnoreCase("GET") && firstLine[1].contains("?")) {
            String[] parms = spilts(firstLine[1], "\\?");
            map.put("parm", parms[parms.length - 1]);
        }
        if (firstLine[0].equalsIgnoreCase("DELETE")) {
            String[] parms = spilts(firstLine[1], "/");
            map.put("parm", parms[parms.length - 1]);
        }
        for (int i = 1; i < s.length; i++) {
            if (s[i].contains("Host")) {
                map.put("host", spilts(s[i], " ")[1]);
                break;
            }
        }
        if (firstLine[0].equalsIgnoreCase("POST") || firstLine[0].equalsIgnoreCase("PUT")) {
            map.put("info", s[s.length - 1]);
        }
        return map;
    }


    private String getContentType(String s) {
        String type;
        String lastName = spilts(s, "\\.")[1];
        if ("html".equals(lastName)) {
            type = "text/html";
        } else if ("css".equals(lastName)) {
            type = "text/css";
        } else if ("js".equals(lastName)) {
            type = "application/x-javascript";
        } else {
            type = "image/" + lastName;
        }
        return type;
    }

    private String getPath(String s) {
        String path;
        if (s.contains("html")) {
            path = HttpServer.HTML_PATH + s;
        } else {
            path = HttpServer.BASE_PATH + s;
        }
        return path;
    }
}
