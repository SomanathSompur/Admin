package com.softwiz.admin.service;

import com.softwiz.admin.entity.Book;
import com.softwiz.admin.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class AdminBookService {
    @Autowired
    private BookRepository bookRepository;


    // Create new book with new title and new ISBN
    public Book createBook(Book book) {

        // Check if a book with the same title already exists
        if (bookRepository.findByTitle(book.getTitle()) != null) {
            throw new IllegalArgumentException("Book with the same title already exists");
        }

        // Check if a book with the same ISBN already exists
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("Book with the same ISBN already exists");
        }

        // Save the new book
        return bookRepository.save(book);
    }


    // Delete Book with existing ID
    public void deleteBook(Long bookId) {
        // Check if a book with the given ID exists
        if (!bookRepository.findById(bookId).isPresent()) {
            throw new IllegalArgumentException("Book with ID " + bookId + " does not exist");
        }
        // Delete the book by ID
        bookRepository.deleteById(bookId);
    }


    /*
    public Book updateBook(Long bookId, Book updatedBook) {

        // Check if a book with the given ID exists
        Optional<Book> existingBookOptional = bookRepository.findById(bookId);
        if (!existingBookOptional.isPresent()) {
            throw new IllegalArgumentException("Book with ID " + bookId + " does not exist");
        }

        // Get the existing book
        Book existingBook = existingBookOptional.get();

        // Update the book fields
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setGenre(updatedBook.getGenre());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setIsAvailable(updatedBook.getIsAvailable());

        // Save and return the updated book
        return bookRepository.save(existingBook);
    }

     */


    // Updating Book details to the existing book with ID that should not repeat ISBN
    public Book updateBook(Long bookId, Book updatedBook) {
        // Check if a book with the given ID exists
        if (!bookRepository.findById(bookId).isPresent()) {
            throw new IllegalArgumentException("Book with ID " + bookId + " does not exist");
        }

        // Check if a book with the same ISBN exists for a different book
        if (bookRepository.findByIdNotAndIsbn(bookId, updatedBook.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("ISBN already exists for another book");
        }

        // Get the existing book
        Book existingBook = bookRepository.findById(bookId).get();

        // Update the book fields
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setGenre(updatedBook.getGenre());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setIsAvailable(updatedBook.getIsAvailable());

        // Save and return the updated book
        return bookRepository.save(existingBook);
    }



    // Search book with ID
    public Book getBookDetails(Long bookId) {

        // Check if a book with the given ID exists
        Optional<Book> existingBookOptional = bookRepository.findById(bookId);
        if (!existingBookOptional.isPresent()) {
            throw new IllegalArgumentException("Book with ID " + bookId + " does not exist");
        }

        // Return the book details
        return existingBookOptional.get();
    }


    /*
    public List<Book> getBooksByAuthor(String author) {
        // Find books by author
        List<Book> booksByAuthor = bookRepository.findByAuthor(author);

        // Check if any books were found
        if (booksByAuthor.isEmpty()) {
            throw new IllegalArgumentException("No books found by author: " + author);
        }

        // Return the list of books
        return booksByAuthor;
    }


    public List<Book> getBooksByGenre(String genre) {
        // Find books by genre
        List<Book> booksByGenre = bookRepository.findByGenre(genre);

        // Check if any books were found
        if (booksByGenre.isEmpty()) {
            throw new IllegalArgumentException("No books found with genre: " + genre);
        }

        // Return the list of books
        return booksByGenre;
    }

     */


    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }


       public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
