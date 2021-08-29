package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloCardDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TrelloClientTestSuite {

    @Autowired
    private TrelloClient client;

    @Test
    void testGetUrl() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name", "description", "position", "list_ID");

        // When
        URI uri = client.getUrl(trelloCardDto);

        // Then
        assertAll(
                () -> assertEquals("api.trello.com", uri.getHost()),
                () -> assertEquals("/1/cards", uri.getPath()),
                () -> assertEquals("https", uri.getScheme())
        );
    }

    @Test
    void testBuildURI() {
        // Given
        // When
        URI uri = client.buildURI();

        // Then
        assertAll(
                () -> assertEquals("api.trello.com", uri.getHost()),
                () -> assertEquals("/1/members/daniel21790792/boards", uri.getPath()),
                () -> assertEquals("https", uri.getScheme())
        );
    }
}