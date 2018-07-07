package pers.vic.httpserver.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by Vic Xu on 2018/7/7
 */
public class ResourceList {
    public static List<String> urlList = new ArrayList<>();

    public static Map<String, String> redirectMap = new HashMap<>();


    public static void initUrlList() {
        urlList.add("/books");
    }

    public static void initRedirectMap() {
        redirectMap.put("aaa.html", "https://www.baidu.com/");
        redirectMap.put("bbb.html", "https://www.baidu.com/");
        redirectMap.put("ccc.html", "https://www.baidu.com/");
    }

    public static boolean urlAccess(String url) {
        boolean flag = false;
        for (String s : urlList) {
            if (url.contains(s)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static String getRedirectUrl(String s) {
        String url = null;
        for (String key:redirectMap.keySet()) {
            if (s.contains(key)) {
                url = redirectMap.get(key);
                break;
            }
        }
        return url;
    }


}
