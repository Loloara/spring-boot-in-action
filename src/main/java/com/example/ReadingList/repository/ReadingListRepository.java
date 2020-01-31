package com.example.ReadingList.repository;

import java.util.List;

import com.example.ReadingList.model.Book;
import com.example.ReadingList.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingListRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByReader(Reader reader);
}
