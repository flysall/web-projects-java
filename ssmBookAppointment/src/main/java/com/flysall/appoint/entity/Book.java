package com.flysall.appoint.entity;

/**
 * 图书
 */
public class Book {
    private long bookId; //图书ID
    private String name; //图书名
    private int number; //光藏数目
    private String introd;  //图书简介

    public Book(){

    }

    public Book(long bookId, String name, int number){
        this.bookId = bookId;
        this.name = name;
        this.number = number;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getIntrod() {
        return introd;
    }

    public void setIntrod(String introd) {
        this.introd = introd;
    }

    @Override
    public String toString() {
        return "Book [bookId=" + bookId + ", name=" + name + ", number=" + number + ", introd=" + introd + "]";
    }
}
