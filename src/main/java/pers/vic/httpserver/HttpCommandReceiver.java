package pers.vic.httpserver;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import pers.vic.httpserver.data.BooksMap;
import pers.vic.httpserver.entity.BooksEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by Vic Xu on 2018/7/6
 * @author Administrator
 * 实现者
 */

public class HttpCommandReceiver {

    public String execteGet(String id) {
       if (StringUtils.isBlank(id)) {
          return JSON.toJSONString(BooksMap.books);
       } else {
           BooksEntity book = BooksMap.getBookWithId(id);
           if (book != null) {
               return JSON.toJSONString(BooksMap.getBookWithId(id));
           } else {
               return null;
           }
       }
    }

    public String exectPut(BooksEntity book) {
        Map<String,String> msg = new HashMap<>(16);
        if (BooksMap.put(book)) {
            msg.put("msg","SUCCESS");
            return JSON.toJSONString(msg);
        }else {
            msg.put("msg","FAILED");
            return JSON.toJSONString(msg);
        }

    }

    public String exectRemove(String id) {
        Map<String,String> msg = new HashMap<>(16);
        if (BooksMap.remove(id)) {
            msg.put("msg","SUCCESS");
            return JSON.toJSONString(msg);
        }else {
            msg.put("msg","FAILED");
            return JSON.toJSONString(msg);
        }
    }

}
