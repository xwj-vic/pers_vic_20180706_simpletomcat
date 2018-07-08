package pers.vic.httpserver.entity;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * Create By Vic Xu on 7/6/2018
 * @author Administrator
 */
@Data
@NoArgsConstructor
public class BooksEntity implements Serializable {


    public BooksEntity(String bookName, double price, String author) {
        this.id = UUID.randomUUID().toString();
        this.bookName = bookName;
        this.price = price;
        this.author = author;
    }

    private String id;

    private String bookName;

    private double price;

    private String author;
}
