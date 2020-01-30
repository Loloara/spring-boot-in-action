package com.example.ReadingList;

import com.example.ReadingList.model.Book;
import com.example.ReadingList.model.Reader;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MockMvcWebTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value="lucas", userDetailsServiceBeanName = "myUserDetailsService")
    public void homePage() throws Exception{
        Reader expectedReader = new Reader();
        expectedReader.setUsername("lucas");
        expectedReader.setPassword("{noop}password");
        expectedReader.setFullname("Lucas Lee");

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attribute("reader", samePropertyValuesAs(expectedReader)))
                .andExpect(model().attribute("books", hasSize(0)))
                .andExpect(model().attribute("amazonID", "loloara-20"));
    }

    @Test
    @WithUserDetails(value="lucas", userDetailsServiceBeanName = "myUserDetailsService")
    public void postBook() throws Exception{
        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "BOOK TITLE")
                .param("author", "BOOK AUTHOR")
                .param("isbn", "1234567890")
                .param("description", "DESCRIPTION").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/"));

        Reader expectedReader = new Reader();
        expectedReader.setUsername("lucas");
        expectedReader.setFullname("Lucas Lee");
        expectedReader.setPassword("{noop}password");

        Book expectedBook = new Book();
        expectedBook.setId(1L);
        expectedBook.setReader(expectedReader);
        expectedBook.setTitle("BOOK TITLE");
        expectedBook.setAuthor("BOOK AUTHOR");
        expectedBook.setIsbn("1234567890");
        expectedBook.setDescription("DESCRIPTION");

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", hasSize(1)))
                .andExpect(model().attribute("books", contains(samePropertyValuesAs(expectedBook))));
    }
}
