package com.bkacad.app.models.dao;

import java.sql.*;
import java.util.Optional;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bkacad.app.exceptions.DatabaseActionException;
import com.bkacad.app.models.entities.Book;

import lombok.Getter;
import lombok.Setter;

@Repository
@Getter
@Setter
public class BookDAOImpl implements BookDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createTable() throws DatabaseActionException {
        try {
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS BOOKS (" +
                    "Id INTEGER AUTO_INCREMENT PRIMARY KEY," +
                    "Title VARCHAR(255)," +
                    "Author VARCHAR(255)," +
                    "YearOfRelease INTEGER)");
        } catch (Exception e) {
            throw new DatabaseActionException();
        }
    }

    @Override
    public void deleteTable() throws DatabaseActionException {
        try {
            jdbcTemplate.execute("DROP TABLE IF EXISTS BOOKS");
        } catch (Exception e) {
            throw new DatabaseActionException();
        }
    }

    @Override
    public void createBook(Book book) throws DatabaseActionException {
        String sql = "INSERT INTO BOOKS(Title,Author,YearOfRelease) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getYearOfRelease());
        } catch (Exception e) {
            throw new DatabaseActionException();
        }
    }

    @Override
    public void deleteBook(int id) throws DatabaseActionException {
        String sql = "DELETE FROM BOOKS WHERE Id=?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new DatabaseActionException();
        }
    }

    @Override
    public void updateBookTitle(int id, String newTitle) throws DatabaseActionException {
        String sql = "UPDATE BOOKS SET Title=? WHERE Id=?";
        try {
            jdbcTemplate.update(sql, newTitle, id);
        } catch (Exception e) {
            throw new DatabaseActionException();
        }
    }

    @Override
    public Optional<Book> findBookById(int id){
        String sql = "SELECT * FROM BOOKS WHERE Id=?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, rowNum) -> Optional.of(mapResultSetToBook(resultSet)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        String sql = "SELECT * FROM BOOKS WHERE Title LIKE CONCAT('%', ?, '%')";
        
        try {
            return jdbcTemplate.query(sql, new Object[]{title}, (resultSet, rowNum) -> new Book()
                    .id(resultSet.getInt("Id"))
                    .title(resultSet.getString("Title"))
                    .author(resultSet.getString("Author"))
                    .yearOfRelease(resultSet.getInt("YearOfRelease")));
        } catch (Exception e) {
            // Return an empty list if an exception occurs
            return Collections.emptyList();
        }
    }

    @Override
    public List<Book> findBookByAuthor(String author) {
        String sql = "SELECT * FROM BOOKS WHERE Author=?";
        
        try {
            return jdbcTemplate.query(sql, new Object[]{author}, (resultSet, rowNum) -> new Book()
                    .id(resultSet.getInt("Id"))
                    .title(resultSet.getString("Title"))
                    .author(resultSet.getString("Author"))
                    .yearOfRelease(resultSet.getInt("YearOfRelease")));
        } catch (Exception e) {
            // Return an empty list if an exception occurs
            return Collections.emptyList();
        }
    }
    @Override
    public List<Book> findBooksByPage(int psize,int pnum){
        String sql = "SELECT * FROM BOOKS LIMIT ? OFFSET ?";
        int limit = psize;
        int offset = limit * (pnum-1);
        try {
            return jdbcTemplate.query(sql,new Object[]{limit,offset}, (resultSet, rowNum) -> new Book()
            .id(resultSet.getInt("Id"))
            .title(resultSet.getString("Title"))
            .author(resultSet.getString("Author"))
            .yearOfRelease(resultSet.getInt("YearOfRelease"))
            );
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }


    @Override
    public List<Book> findAll() throws DatabaseActionException {
        String sql = "SELECT * FROM BOOKS ";
        try {
            return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Book()
            .id(resultSet.getInt("Id"))
            .title(resultSet.getString("Title"))
            .author(resultSet.getString("Author"))
            .yearOfRelease(resultSet.getInt("YearOfRelease"))
            );
        } catch (Exception e) {
            throw new DatabaseActionException();
        }
    }

    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("Id");
        String title = resultSet.getString("Title");
        String author = resultSet.getString("Author");
        int yearOfRelease = resultSet.getInt("YearOfRelease");
        return new Book().id(id).title(title).author(author).yearOfRelease(yearOfRelease);
    }
}
