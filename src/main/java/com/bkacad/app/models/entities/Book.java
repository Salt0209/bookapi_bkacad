package com.bkacad.app.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(fluent = true,chain = true)
public class Book {
    private int id;
    private String title;
    private String author;
    private int yearOfRelease;

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public int getYearOfRelease() {
        return yearOfRelease;
    }  
}
