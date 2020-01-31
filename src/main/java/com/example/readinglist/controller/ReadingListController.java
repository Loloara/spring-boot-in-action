package com.example.readinglist.controller;

import java.util.List;

import com.example.readinglist.config.AmazonProperties;
import com.example.readinglist.model.Book;
import com.example.readinglist.model.Reader;
import com.example.readinglist.repository.ReadingListRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ReadingListController {
    private final ReadingListRepository readingListRepository;
    private final AmazonProperties amazonProperties;

    public ReadingListController(ReadingListRepository readingListRepository, AmazonProperties amazonProperties){
        this.readingListRepository = readingListRepository;
        this.amazonProperties = amazonProperties;
    }

    @GetMapping
    public String readersBooks(Reader reader, Model model){
        List<Book> readingList = readingListRepository.findAllByReader(reader);
        if(readingList != null){
            model.addAttribute("books", readingList);
            model.addAttribute("reader", reader);
            model.addAttribute("amazonID", amazonProperties.getAssociateId());
        }

        return "readingList";
    }

    @PostMapping
    public String addToReadingList(Reader reader, Book book){
        book.setReader(reader);
        readingListRepository.save(book);

        return "redirect:/";
    }
}
