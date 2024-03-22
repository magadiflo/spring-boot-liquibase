package dev.magadiflo.springbootliquibase.controllers;

import dev.magadiflo.springbootliquibase.entities.Library;
import dev.magadiflo.springbootliquibase.repositories.LibraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/libraries")
public class LibraryController {

    private final LibraryRepository libraryRepository;

    @GetMapping
    public ResponseEntity<List<Library>> getAllLibraries() {
        return ResponseEntity.ok((List<Library>) this.libraryRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Library> saveLibrary(@RequestBody Library library) {
        Library libraryDB = this.libraryRepository.save(library);
        return new ResponseEntity<>(libraryDB, HttpStatus.CREATED);
    }
}
