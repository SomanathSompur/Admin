package com.softwiz.admin.controller;

import com.softwiz.admin.entity.Book;
import com.softwiz.admin.service.AdminBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/book")
public class AdminBookController {


    @Autowired
    private AdminBookService adminBookService;

    /*
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return adminBookService.createBook(book);
    }
    */

    // Create new book with new title and new ISBN
    @PostMapping("/create_book")
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        try {
            Book createdBook = adminBookService.createBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body("Book created with ID: " + createdBook.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        adminBookService.deleteBook(id);
    }
    */


    // Delete Book with existing ID
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId) {
        try {
            adminBookService.deleteBook(bookId);
            return ResponseEntity.ok("Book with ID " + bookId + " deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /*
      @PutMapping("/{id}")
          public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
          return adminBookService.updateBook(id, book);
     }
     */


    // Updating Book details to the existing book with ID that should not repeat ISBN
    @PutMapping("/update/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable Long bookId, @RequestBody Book updatedBook) {
        try {
            Book updated = adminBookService.updateBook(bookId, updatedBook);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    // Search book with ID
    @GetMapping("/details/{bookId}")
    public ResponseEntity<?> getBookDetails(@PathVariable Long bookId) {
        try {
            Book bookDetails = adminBookService.getBookDetails(bookId);
            return ResponseEntity.ok(bookDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /*

    // Search book with author

    @GetMapping("/byAuthor/{author}")
    public ResponseEntity<?> getBooksByAuthor(@RequestParam String author) {
        try {
            List<Book> books = adminBookService.getBooksByAuthor(author);
            return ResponseEntity.ok(books);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

        // Search book with genre

    @GetMapping("/byGenre/{genre}")
    public ResponseEntity<?> getBooksByGenre(@RequestParam String genre) {
        try {
            List<Book> books = adminBookService.getBooksByGenre(genre);
            return ResponseEntity.ok(books);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

     */


    @GetMapping("/by_author")
    public List<Book> getBooksByAuthor(@RequestParam String author) {
        return adminBookService.getBooksByAuthor(author);
    }

    @GetMapping("/by_genre")
    public List<Book> getBooksByGenre(@RequestParam String genre) {
        return adminBookService.getBooksByGenre(genre);
    }



    // It shows list of all book details
    @GetMapping
    public List<Book> getAllBooks() {
        return adminBookService.getAllBooks();
    }
}
