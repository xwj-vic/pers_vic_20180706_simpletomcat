package pers.vic.simpletomcat.entity;


import java.io.Serializable;
import java.util.UUID;

/**
 * Create By Vic Xu on 7/6/2018
 *
 * @author Administrator
 */
public class BooksEntity implements Serializable {

    public BooksEntity() {
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
