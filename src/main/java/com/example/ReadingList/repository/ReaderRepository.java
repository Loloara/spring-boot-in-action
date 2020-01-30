package com.example.ReadingList.repository;

import com.example.ReadingList.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, String> {
    Reader findByUsername(String username);
}
