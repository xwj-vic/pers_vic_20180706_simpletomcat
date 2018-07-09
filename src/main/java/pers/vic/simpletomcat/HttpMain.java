package pers.vic.simpletomcat;

import pers.vic.simpletomcat.data.BooksMap;
import pers.vic.simpletomcat.data.ResourceList;
import pers.vic.simpletomcat.http.HttpRequest;

import java.io.IOException;

/**
 * Create by Vic Xu on 2018/7/6
 *
 * @author Administrator
 */
public class HttpMain {


    public static void main(String[] args) throws IOException {
        BooksMap.initBooks();
        ResourceList.initUrlList();
        ResourceList.initUrlSuffixList();
        ResourceList.initRedirectMap();
        new HttpRequest(9999).connect();
    }
}
