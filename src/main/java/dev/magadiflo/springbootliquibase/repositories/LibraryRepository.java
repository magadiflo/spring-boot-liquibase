package dev.magadiflo.springbootliquibase.repositories;

import dev.magadiflo.springbootliquibase.entities.Library;
import org.springframework.data.repository.CrudRepository;

public interface LibraryRepository extends CrudRepository<Library, Long> {
}
