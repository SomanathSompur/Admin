package com.softwiz.admin.repository;

import com.softwiz.admin.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Book findByTitle(String title);
    Optional<Book> findById(Long id);
    Optional<Book> findByIsbn(String isbn);
    Optional<Book> findByIdNotAndIsbn(Long id, String isbn);

    List<Book> findByAuthor(String author);
    List<Book> findByGenre(String genre);

}
