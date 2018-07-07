package pers.vic.httpserver.entity;

import lombok.*;

import java.util.UUID;

/**
 * Create By Vic Xu on 7/6/2018
 */
@Data
@NoArgsConstructor
public class BooksEntity {


    public BooksEntity(String bookName, String price, String author) {
        this.id = UUID.randomUUID().toString();
        this.bookName = bookName;
        this.price = price;
        this.author = author;
    }

    private String id;

    private String bookName;

    private String price;

    private String author;
}
