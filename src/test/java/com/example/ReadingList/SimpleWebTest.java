package com.example.ReadingList;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SimpleWebTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void pageNotFound(){
        Exception exception = assertThrows(HttpClientErrorException.class, () -> {
            RestTemplate rest = new RestTemplate();
            rest.getForObject("http://localhost:{port}/lucasPage", String.class, port);
        });

        String expectedMessage = "404";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
