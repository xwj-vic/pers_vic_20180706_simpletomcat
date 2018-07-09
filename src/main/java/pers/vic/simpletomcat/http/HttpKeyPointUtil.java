package pers.vic.simpletomcat.http;

import pers.vic.simpletomcat.data.BaseData;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By Vic Xu on 7/9/2018
 * feature information
 */
public class HttpKeyPointUtil {

    Map<String, String> getKeyPoint(String[] s) {
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


    String getContentType(String s) {
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

    String getPath(String s) {
        String path;
        if (s.contains("html")) {
            path = BaseData.HTML_PATH + s;
        } else {
            path = BaseData.BASE_PATH + s;
        }
        return path;
    }

    public static String[] spilts(String s, String reg) {
        return s.split(reg);
    }
}
