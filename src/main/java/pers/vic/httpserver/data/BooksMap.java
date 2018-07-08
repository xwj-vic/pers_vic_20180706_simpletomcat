package pers.vic.httpserver.data;

import pers.vic.httpserver.entity.BooksEntity;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by Vic Xu on 2018/7/6
 */

public class BooksMap {

    public static ConcurrentHashMap<String, BooksEntity> books;

    public static void initBooks() {
        books = new ConcurrentHashMap<>(16);
        for (int i = 0; i < 100; i++) {
            BooksEntity book = new BooksEntity("Book" + i, Math.ceil(Math.random() * 100 + 10), "author" + i);
            books.put(book.getId(), book);
        }
    }

    public static boolean remove(String id) {
        if (!books.containsKey(id)) {
            return false;
        }
        books.remove(id);
        return true;
    }

    public static boolean put(BooksEntity book) {
        books.put(book.getId(), book);
        return true;
    }

    public static BooksEntity getBookWithId(String id) {
        return books.getOrDefault(id, null);
    }

}
