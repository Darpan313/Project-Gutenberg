package com.example.catalog;

public class Catalog {
    private  String author;
    private  String title;
    public Catalog(String author, String title){
        this.author = author;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public String getTitle(){
        return title;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setTitle(String title){
        this.title = title;
    }
}
