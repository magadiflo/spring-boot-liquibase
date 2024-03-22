package dev.magadiflo.springbootliquibase.controllers;

import dev.magadiflo.springbootliquibase.entities.Book;
import dev.magadiflo.springbootliquibase.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/books")
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok((List<Book>) this.bookRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        Book bookDB = this.bookRepository.save(book);
        return new ResponseEntity<>(bookDB, HttpStatus.CREATED);
    }
}
