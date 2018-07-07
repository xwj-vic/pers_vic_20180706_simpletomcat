package pers.vic.httpserver.http;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Objects;

/**
 * Create by Vic Xu on 2018/7/7
 */
@Data
@AllArgsConstructor
public class HttpRequest {
    private static final String BASE_PATH = "src/main/resources";
    private static final String HTML_PATH = BASE_PATH + "/html";

    private Socket socket;

    void doRequestUtil(String json, String host) {
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

    void doRequestFile(File file, String type) {
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

    void doRedirct(String url) {
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

    void doExceptionRequest(String type) {
        PrintStream print = null;
        FileInputStream fis = null;
        File file = null;
        try {
            print = new PrintStream(socket.getOutputStream(), true);
            if ("404".equalsIgnoreCase(type)) {
                file = new File(HTML_PATH + "/404.html");
                print.println("HTTP/1.1 404 Not found");
            } else if ("500".equalsIgnoreCase(type)) {
                print.println("HTTP/1.1 500 Error");
                file = new File(HTML_PATH + "/500.html");
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
