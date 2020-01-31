package com.example.readinglist.repository;

import java.util.List;

import com.example.readinglist.model.Book;
import com.example.readinglist.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingListRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByReader(Reader reader);
}
