package com.bkacad.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.bkacad.app.exceptions.DatabaseActionException;
import com.bkacad.app.models.dao.BookDAO;
import com.bkacad.app.models.entities.Book;

import java.util.List;
import java.util.Optional;;

@Controller
@CrossOrigin
@RequestMapping("/api")
public class BookAPIController {
    @Autowired
    private BookDAO bookDAO;

    @RequestMapping(value = {"/books","/books/all"},method = RequestMethod.GET)
    public ResponseEntity<Object> getBooks() throws DatabaseActionException{
        List<Book> lBooks = bookDAO.findAll();
        return new ResponseEntity<Object>(lBooks,HttpStatus.OK);
    }
    @RequestMapping(value = {"/books"},method = RequestMethod.GET, params = {"page_size", "page_num"})
    public ResponseEntity<Object> getBooksByPage(@RequestParam("page_size") int psize,@RequestParam("page_num") int pnum) throws DatabaseActionException{
        List<Book> lBooks = bookDAO.findBooksByPage(psize,pnum);
        return new ResponseEntity<Object>(lBooks,HttpStatus.OK);
    }
    @RequestMapping(value = "/books",method = RequestMethod.GET, params = "id")
    public ResponseEntity<Object> findBookById(@RequestParam("id") int id){
        Optional<Book> book = bookDAO.findBookById(id);
        if(book.isPresent()){
            return new ResponseEntity<Object>(book.get(),HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Can't find this id: "+id,HttpStatus.NOT_FOUND);
    }
    @RequestMapping(value = "/books",method = RequestMethod.GET, params = "title")
    public ResponseEntity<Object> findBookByTitle(@RequestParam("title") String title){
        List<Book> book = bookDAO.findBookByTitle(title);
        if(!book.isEmpty()){
            return new ResponseEntity<Object>(book,HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Can't find this title: "+title,HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/books",method = RequestMethod.GET, params = "author")
    public ResponseEntity<Object> findBookByAuthor(@RequestParam("author") String name){
        List<Book> lBooks = bookDAO.findBookByAuthor(name);
        if(!lBooks.isEmpty()){
            return new ResponseEntity<Object>(lBooks,HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Can't find the author name: "+name,HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/books/create",method = RequestMethod.POST)
    public ResponseEntity<String> createBook(@RequestBody Book book) throws DatabaseActionException{
        bookDAO.createBook(book);
        return new ResponseEntity<String>("Created",HttpStatus.OK);
    }

    @RequestMapping(value = "/books/update/{id}",method = RequestMethod.PUT)
    public ResponseEntity<String> updateBookTitle(@PathVariable int id,@RequestBody Book book) throws DatabaseActionException{
        Optional<Book> existingBook = bookDAO.findBookById(id);
        if(existingBook.isPresent()){
            bookDAO.updateBookTitle(id,book.getTitle());
            return new ResponseEntity<String>("Updated",HttpStatus.OK);
        }
        return new ResponseEntity<>("Update failed",HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/books/delete/{id}",method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteBook(@PathVariable int id) throws DatabaseActionException{
        Optional<Book> existingBook = bookDAO.findBookById(id);
        if(existingBook.isPresent()){
            bookDAO.deleteBook(id);
            return new ResponseEntity<String>("Deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("Delete failed",HttpStatus.BAD_REQUEST);
    }










    
}
