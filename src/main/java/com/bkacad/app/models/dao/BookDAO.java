package com.bkacad.app.models.dao;

import java.util.List;
import java.util.Optional;

import com.bkacad.app.exceptions.DatabaseActionException;
import com.bkacad.app.models.entities.Book;

public interface BookDAO {
    void createTable() throws DatabaseActionException;
    void deleteTable() throws DatabaseActionException;
    void createBook(final Book book) throws DatabaseActionException;
    void deleteBook(int id) throws DatabaseActionException;
    void updateBookTitle(int id, String newTitle) throws DatabaseActionException;
    Optional<Book> findBookById(int id);
    List<Book> findBookByTitle(String title);
    List<Book> findBookByAuthor(String author);
    List<Book> findBooksByPage(int psize, int pnum);
    List<Book> findAll() throws DatabaseActionException;
}
