package pers.vic.simpletomcat.reveiver;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import pers.vic.simpletomcat.data.BooksMap;
import pers.vic.simpletomcat.entity.BooksEntity;
import pers.vic.simpletomcat.http.HttpKeyPointUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by Vic Xu on 2018/7/6
 *
 * @author Administrator
 *
 */

public class BooksCommandReceiver implements CommandReceiver {

    @Override
    public String execteGet(Map<String, String> map) {
        String id = null;
        if (map.containsKey("parm")) {
            id = HttpKeyPointUtil.spilts(map.get("parm"), "=")[1];
        }
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

    @Override
    public String exectPut(Map<String, String> map) {
        BooksEntity booksEntity = null;
        if (map.containsKey("info")) {
            String[] infos = HttpKeyPointUtil.spilts(map.get("info"), "&");
            Map<String, String> value = new HashMap<>(16);
            for (String s : infos) {
                String[] item = HttpKeyPointUtil.spilts(s, "=");
                value.put(item[0], item[1]);
            }
            booksEntity = new BooksEntity(value.get("bookName"), Double.valueOf(value.get("price")), value.get("author"));
            if (value.containsKey("id")) {
                booksEntity.setId(value.get("id"));
            }
        }
        Map<String, String> msg = new HashMap<>(16);
        if (BooksMap.put(booksEntity)) {
            msg.put("msg", "SUCCESS");
            return JSON.toJSONString(msg);
        } else {
            msg.put("msg", "FAILED");
            return JSON.toJSONString(msg);
        }
    }

    @Override
    public String exectRemove(Map<String, String> map) {
        Map<String, String> msg = new HashMap<>(16);
        if (BooksMap.remove(map.get("parm"))) {
            msg.put("msg", "SUCCESS");
            return JSON.toJSONString(msg);
        } else {
            msg.put("msg", "FAILED");
            return JSON.toJSONString(msg);
        }
    }

}
