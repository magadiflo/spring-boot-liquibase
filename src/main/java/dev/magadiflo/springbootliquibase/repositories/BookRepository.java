package dev.magadiflo.springbootliquibase.repositories;

import dev.magadiflo.springbootliquibase.entities.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
